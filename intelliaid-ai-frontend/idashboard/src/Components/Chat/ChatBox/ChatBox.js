import React from "react";
import Message from "../Messages/Messages";
import './ChatBox.scss'
import { useParams } from "react-router";

const ChatBox = () => {
    let { conversationId } = useParams();
    return(
        <div className="chatBoxMain">
            <Message conversationId={conversationId}/>
        </div>
    );
};

export default ChatBox;