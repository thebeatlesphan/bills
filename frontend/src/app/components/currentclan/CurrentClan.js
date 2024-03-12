import { useState } from "react";
import { useAuth } from "../context/Context";
import Button from "../button/Button";
import styles from "./CurrentClan.module.css";
import Form from "../form/Form";
import InputField from "../form/InputField";

const CurrentClan = (...props) => {
  const { userId, currentClan, clans, selectClan, clanList, clanExpenses } =
    useAuth();
  const [isOpen, setIsOpen] = useState(false);
  const [newMember, setNewMember] = useState("");
  const [members, setMembers] = useState(null);
  const transClass = isOpen ? styles.clans : styles.hidden;

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
      setMembers(reply.data);
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
      console.log(_reply.data);
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
          <div className={styles.current}>Clan {currentClan}</div>
          <div className={styles.buttons}>
            <Form title="Member" onSubmit={handleSubmitNewMember}>
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
            <Button label="Remove Member" />
            <Button label="Delete Clan" />
          </div>
          <div>
            <div>Clan Members</div>
            {members == null ? (
              <></>
            ) : (
              members.map((member) => <li key={member.username}>{member.username}</li>)
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default CurrentClan;
