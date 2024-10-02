import React, {useState} from "react";




const TextBox = ({conversationId}) =>{
    let [inputChat, setInputChat] = useState("");

let handleSend = () => {
    console.log("sent");
    setInputChat("");
};
    return(
        <div className="inputBoxDiv">
            <form className="MessageInputForm" onSubmit={handleSend}>
                <input type="text" value={inputChat} onChange={(i) => i.target.value} placeholder="What do you wanna ask ?"/>
                <button type="submit">Send</button>
            </form>
        </div>
    );
};

export default TextBox;