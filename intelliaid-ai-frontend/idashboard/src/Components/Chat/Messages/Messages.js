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
            
            // const profilePromises = uniqueProfileIds.map((profileId) =>
            //     axios.get(`http://127.0.0.1:8080/profile/id=${encodeURIComponent(profileId)}`)
            //     .then((response) => ({profileId, data: response.data}))
            //     .catch((error) => {
            //         console.log(`Error fetching profile for profileId ${profileId}:`, error);
            //         return {profileId, data: null};
            //     })
            // );

            const profileResponse = {};
            for(let i = 0; i < uniqueProfileIds.length; i++){
                let resp = await axios.get('http://127.0.0.1:8080/profile/id='+uniqueProfileIds[i]);
                profileResponse[i] = resp;
            }
            
            // let profileResponses = await Promise.allSettled(profilePromises);

            let profileData = {};

            profileResponse.forEach((profileResponse, index) => {
                const profileId = profileResponse.profileId;
                if(profileResponse.data){
                    profileData[profileId] = profileResponse.data;
                }
                else{
                    console.log("Not Found");
                }
            });
            setProfiles(profileData);
            console.log(profileData);
        }
        catch(error){
            console.error('Error fetching messages: ', error);
        }
    };
    getMessages();
}, [conversationId]);

return (
  <div className="messageWindow">
      {messages ? (
        <div>
          {messages.messages.map((msg, index) => (
            <p key={index}>
                <strong>
                    {profiles[msg.profileId]?.firstName || profiles[msg.profileId]?.lastName || "Loading..."}
                </strong>
            : {msg.messageText}</p>
          ))}
        </div>
      ) : (
        <p>Loading messages...</p>
      )}
  </div>
);
}

export default Message;