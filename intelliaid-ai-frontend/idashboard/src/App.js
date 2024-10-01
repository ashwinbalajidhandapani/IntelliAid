import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ChatBox from './Components/Chat/ChatBox/ChatBox';


function App(){
    return (
      <Router>
        <Routes>
          <Route path='/conversation/:conversationId' Component={ChatBox}/>
        </Routes>
      </Router>
    );
}


export default App;
