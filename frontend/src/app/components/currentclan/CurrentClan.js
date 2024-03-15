import { useState, useRef } from "react";
import { useAuth } from "../context/Context";
import Button from "../button/Button";
import styles from "./CurrentClan.module.css";
import Form from "../form/Form";
import InputField from "../form/InputField";

const CurrentClan = (props) => {
  const {
    userId,
    currentClan,
    clans,
    selectClan,
    clanList,
    clanExpenses,
    members,
    membersList,
  } = useAuth();
  const [isOpen, setIsOpen] = useState(false);
  const [newMember, setNewMember] = useState("");
  const [canDelete, setCanDelete] = useState(false);
  const [deleteMembers, setDeleteMembers] = useState([]);
  const transClass = isOpen ? styles.clans : styles.hidden;
  const dialogRef = useRef();

  const showModal = () => {
    dialogRef.current.showModal();
  };

  const onCancel = () => {
    dialogRef.current.close();
  };

  const handleClansList = async () => {
    // toggle clan list dropdown
    setIsOpen((prev) => !prev);

    // We are using JPA repository rest resource param query here
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromUserId?userId=${userId}`;
    const response = await fetch(url);

    if (!response.ok) {
      window.alert("Error retrieving available clans.");
    } else {
      const reply = await response.json();
      clanList(reply.data);
    }
  };

  const handleClanUsers = async (clanName) => {
    // Update context first
    selectClan(clanName);
    setIsOpen((prev) => !prev);

    // Current members list
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromClanName?clanName=${clanName}`;
    const response = await fetch(url);

    if (!response.ok) {
      window.alert("Failed to get clan members.");
    } else {
      const reply = await response.json();
      membersList(reply.data);
    }

    // Current expenses list
    const _url = `${process.env.NEXT_PUBLIC_API}api/expense/getClanExpenses?clanName=${clanName}`;
    const token = sessionStorage.getItem("jwtToken");
    const _response = await fetch(_url, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!_response.ok) {
      window.alert("Failed to get clan expenses.");
    } else {
      const _reply = await _response.json();
      clanExpenses(_reply.data);
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

    if (!response.ok) {
      window.alert("Failed to add user");
      setNewMember("");
    } else {
      const reply = await response.json();
      setNewMember("");
    }
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

    if (!response.ok) {
      window.alert("Cannot delete clan");
      handleCanDelete();
    } else {
      const reply = await response.json();
    }
  };

  const handleCheckboxChange = (e) => {
    const memberId = e.target.value;
    if (e.target.checked) {
      setDeleteMembers((prev) => [...prev, memberId]);
    } else {
      setDeleteMembers((prev) => prev.filter((id) => id !== memberId));
    }
  };

  const handleRemoveMembers = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/Members`;
    const jwtToken = sessionStorage.getItem("jwtToken");
    const data = { data: [currentClan, ...deleteMembers] };
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
      window.alert("Failed to remove members");
      console.log(reply);
    } else {
      console.log(reply);
    }
  };

  return (
    <div className={styles.dropdown}>
      <Button
        label={currentClan == null ? "Select Clan" : "Change Clan"}
        onClick={handleClansList}
      />
      {clans == null ? (
        <></>
      ) : (
        <div>
          {clans.map((clan) => (
            <li
              key={clan.id}
              className={transClass}
              onClick={() => handleClanUsers(clan.clanName)}
            >
              {clan.clanName}
            </li>
          ))}
        </div>
      )}
      {currentClan == null ? (
        ""
      ) : (
        <div>
          <div className={styles.current}>
            Clan {currentClan}
            <Button label="Manage Clan" onClick={showModal} />
          </div>
          <dialog className={styles.dialog} ref={dialogRef}>
            <div className={styles.dialogcontainer}>
              <div className={styles.buttons}>
                <Form title="Add" onSubmit={handleSubmitNewMember}>
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
                <Form title="Remove" onSubmit={handleRemoveMembers}>
                  {members == null ? (
                    <></>
                  ) : (
                    members.map((member) => (
                      <InputField
                        key={member.id}
                        type="checkbox"
                        label={member.username}
                        value={member.id}
                        onChange={handleCheckboxChange}
                      />
                    ))
                  )}
                  <Button type="submit" label="Remove Selected Member(s)" />
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
              </div>
              <Button label="Close" onClick={onCancel} />
            </div>
          </dialog>
          <div className={styles.clanMembersTitle}>Clan Members</div>
          <div className={styles.clanMembers}>
            {members == null ? (
              <></>
            ) : (
              members.map((member) => (
                <div key={member.username}>{member.username}</div>
              ))
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default CurrentClan;
