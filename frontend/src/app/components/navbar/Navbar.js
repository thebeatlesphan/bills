import { useState, useRef } from "react";
import { useAuth } from "../context/Context";
import styles from "./Navbar.module.css";
import Button from "../button/Button";
import Form from "../form/Form";
import InputField from "../form/InputField";

function Navbar() {
  const {
    username,
    logout,
    currentClan,
    members,
    addMember,
    removeMember,
    deleteClan,
    createClan,
  } = useAuth();
  const [canDelete, setCanDelete] = useState(false);
  const [newMember, setNewMember] = useState("");
  const [clanForm, setClanForm] = useState("");
  const dialogRef = useRef();

  const showModal = () => {
    dialogRef.current.showModal();
  };

  const closeModal = (e) => {
    dialogRef.current.close();
  };

  const handleCanDelete = () => {
    setCanDelete((prev) => !prev);
  };

  const handleDeleteClan = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/delete?clanId=${currentClan.clanId}`;
    const jwtToken = sessionStorage.getItem("jwtToken");
    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
      },
    });

    const reply = await response.json();
    console.log(reply);
    if (!response.ok) {
      window.alert(reply.message);
      handleCanDelete();
    } else {
      deleteClan(currentClan.clanName);
      handleCanDelete();
    }
  };

  const handleRemoveMember = async (memberId) => {
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/removeMember`;
    const jwtToken = sessionStorage.getItem("jwtToken");
    const data = {
      clanName: currentClan.clanName,
      memberId: memberId,
      clanId: currentClan.clanId,
    };

    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    console.log(reply);
    if (!response.ok) {
      window.alert(reply.message);
    } else {
      removeMember(memberId);
    }
  };

  const handleNewMember = (e) => {
    setNewMember(e.target.value);
  };

  const handleAddMember = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/addUserToClan`;
    const token = sessionStorage.getItem("jwtToken");
    const data = {
      username: newMember,
      clanName: currentClan.clanName,
      clanId: currentClan.clanId,
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    console.log(reply);
    if (!response.ok) {
      window.alert(reply.message);
      setNewMember("");
    } else {
      addMember(reply.data);
      setNewMember("");
    }
  };

  const handleClanChange = (e) => {
    setClanForm(e.target.value);
  };

  const handleCreateClan = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/createClan`;
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

    const reply = await response.json();
    console.log(reply);
    if (!response.ok) {
      window.alert("Failed to add clan. Please try again.");
      setClanForm("");
    } else {
      createClan(reply.data);
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
              <Form title="Add Member" onSubmit={handleAddMember}>
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

          <Form title="Create Clan" onSubmit={handleCreateClan}>
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
