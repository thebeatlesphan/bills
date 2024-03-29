import { useState, useRef } from "react";
import { useAuth } from "../context/Context";
import styles from "./Navbar.module.css";
import Button from "../button/Button";
import Form from "../form/Form";
import InputField from "../form/InputField";

function Navbar() {
  const { username, logout, currentClan, members } = useAuth();
  const [canDelete, setCanDelete] = useState(false);
  const [newMember, setNewMember] = useState("");
  const [clanForm, setClanForm] = useState("");
  const dialogRef = useRef();

  const showModal = () => {
    dialogRef.current.showModal();
  };

  const closeModal = (e) => {
    console.log(e);
    dialogRef.current.close();
  };

  const handleCanDelete = () => {
    setCanDelete((prev) => !prev);
  };

  const handleDeleteClan = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/delete?clanName=${currentClan}`;
    const jwtToken = sessionStorage.getItem("jwtToken");
    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
      },
    });

    const reply = await response.json();
    if (!response.ok) {
      window.alert(reply.message);
      handleCanDelete();
    } else {
    }
  };

  const handleRemoveMember = async (memberId) => {
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/removeMember`;
    const jwtToken = sessionStorage.getItem("jwtToken");
    const data = { clanName: currentClan, memberId: memberId };

    console.log(data);
    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    if (!response.ok) {
      window.alert(reply.message);
    } else {
      console.log(reply);
    }
  };

  const handleNewMember = (e) => {
    setNewMember(e.target.value);
  };

  const handleSubmitNewMember = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/addUserToClan`;
    const token = sessionStorage.getItem("jwtToken");
    const data = { username: newMember, clanName: currentClan };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    if (!response.ok) {
      window.alert(reply.message);
      setNewMember("");
    } else {
      setNewMember("");
    }
  };

  const handleClanChange = (e) => {
    setClanForm(e.target.value);
  };

  const handleClanSubmit = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/add`;
    const token = sessionStorage.getItem("jwtToken");
    const data = {
      clanName: clanForm,
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      window.alert("Failed to add clan. Please try again.");
      setClanForm("");
    } else {
      setClanForm("");
    }
  };

  return (
    <nav className={styles.navbar}>
      <div className={styles.unordered}>
        <div className={styles.username} onClick={showModal}>
          Welcome {username}
        </div>
        <span className={styles.gearbox} onClick={showModal}>
          &#9881;
        </span>
      </div>
      <dialog ref={dialogRef} className={styles.dialog}>
        <div className={styles.menu}>
          {currentClan == null ? (
            <></>
          ) : (
            <>
              <Form title="Add Member" onSubmit={handleSubmitNewMember}>
                <InputField
                  type="text"
                  label="Username"
                  value={newMember}
                  onChange={handleNewMember}
                />
                <Button
                  type="submit"
                  label="Add Member"
                  disabled={newMember == "" ? true : false}
                />
              </Form>

              <Form title="Remove Member">
                {members == null ? (
                  <></>
                ) : (
                  members.map((member) => (
                    <div className={styles.removeMember} key={member.id}>
                      <span>{member.username}</span>
                      <span
                        className={styles.x}
                        onClick={() => handleRemoveMember(member.id)}
                      >
                        &#9746;
                      </span>
                    </div>
                  ))
                )}
              </Form>

              <Form title="Delete Clan" onSubmit={handleDeleteClan}>
                <label>Are you sure you want to delete this clan?</label>
                <input
                  type="checkbox"
                  onChange={handleCanDelete}
                  value={canDelete}
                ></input>
                <Button
                  type="submit"
                  label="DELETE"
                  disabled={!canDelete ? true : false}
                />
              </Form>
            </>
          )}

          <Form title="Create Clan" onSubmit={handleClanSubmit}>
            <InputField
              type="text"
              label="Clan Name"
              value={clanForm}
              onChange={handleClanChange}
            />
            <Button
              type="submit"
              label="Create Clan"
              disabled={clanForm == "" ? true : false}
            />
          </Form>
          <Button onClick={logout} label="Sign Out" />
          <Button label="Close" onClick={closeModal} />
        </div>
      </dialog>
    </nav>
  );
}

export default Navbar;
