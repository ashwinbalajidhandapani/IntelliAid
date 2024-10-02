import React from "react";
import Message from "../Messages/Messages";
import './ChatBox.scss'
import { useParams } from "react-router";
import TextBox from "../TextBox/TextBox";

const ChatBox = () => {
    let { conversationId } = useParams();
    return(
        <div className="chatBoxMain">
            <Message conversationId={conversationId}/>
            <TextBox conversation={conversationId}/>
        </div>
    );
};

export default ChatBox;