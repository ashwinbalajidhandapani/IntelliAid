import React, {useEffect, useState} from "react";
import axios from "axios";


let Message = ({ conversationId }) => {
    const[messages, setMessages] = useState(null);
    const[profiles, setProfiles] = useState({});

    useEffect(() => {
        let getMessages = async () => {
        try{
            let response = await axios.get('http://127.0.0.1:8080/conversation/conversation-id='+conversationId);
            setMessages(response.data);

            // Fetch profiles for each unique profileId from the response data stores it to a Set. 
            
            let uniqueProfileIds = [...new Set(response.data.messages.map((msg) => msg.profileId))];
            
            const profileData = {};
            for(let id of uniqueProfileIds){
                console.log(`http://127.0.0.1:8080/profile/id=${id}`);
                let profileIdsResponse = await axios.get(`http://127.0.0.1:8080/profile/id=${id}`);
                profileData[id] = profileIdsResponse.data;
            }
            setProfiles(profileData);
            console.log(profileData);
        }
        catch(error){
            console.error('Error fetching messages: ', error);
        }
    };
    getMessages();
}, [conversationId]);

let parseListedItems = (text) => {
    return text.split('*').map((i)=> i.trim()).filter((i) => i.length > 0);
};

return (
  <div className="messageWindow">
      {messages ? (
        <div className="chat">
          {messages.messages.map((msg, index) => {
              let listItems = parseListedItems(msg.messageText);
              return(
                  <div key = {index}>
                    <strong>
                        {profiles[msg.profileId]?.firstName || "Loading..."}
                    </strong>
                    {listItems.length > 1 ? (
                        <div>
                            <p>{listItems[0]}</p>
                            {listItems.length > 1 && (
                                <ul>
                                    {listItems.slice(1).map((i, index) => (
                                        <li key={index}>
                                            {i.split('\n').map((text, j) => (
                                                <span key={j}>
                                                    {text}
                                                    <br/>
                                                </span>
                                            ))}
                                        </li>
                                    ))}
                                </ul>
                            )}
                        </div>
                    ) : (
                        <p>
                            {msg.messageText.split('\n').map((text, index) => (
                            <span key={index}>
                                {text}
                                <br />
                            </span>
                        ))}
                        </p>
                    )}
                </div>);
          })}
          </div>
      ) : (
          <p>Loading Messages</p>
      )}
      </div>);
}

export default Message;