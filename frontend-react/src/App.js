import logo from './logo.svg';
import './App.css';
import { useState } from 'react';
import { useEffect } from 'react';
import { EventThinner} from './EventThinner.js';

// form to input cv, on edit, upload to server
// on server, each upload gets a new token (cache the last 100?)
// then update the token which is part of the pdf GET request?

const delta = 5000;
const shortDelta = 1000;
const eventThinner = new EventThinner(delta, shortDelta);

function App() {
    
    const defaultToken = '_default';
    const [token, setToken] = useState(defaultToken);
    const [text, setText] = useState('');

    // NB we use "defaultToken" here and _not_ "token" state because we don't want this to re-run each time the token changes 
    useEffect(() => {
            fetch(`/api/v1/yamlFromToken?token=${defaultToken}`,
                  {"mode": "same-origin",
                   "credentials": "include"
                  })
                .then(response => {if (response.ok){
                    response.text().then(data => {
                        setText(data);
                    })
                }})

    }, []);
    
    return (
        <div className="App">
            <div>
                <i>Character</i>Value - instantly generate a CV in PDF using Spring and React.
            </div>
            <div className="row">
                <div className="column">
                    <textarea name="cvYaml" value={text} onChange={(event) =>
                                  {
                                      setText(event.target.value);
                                      eventThinner.run(() => {
                                          fetch('/api/v1/uploadYaml',
                                                {"method": "POST",
                                                 "mode": "same-origin",
                                                 "credentials": "include",
                                                 "headers": {
                                                     "Content-Type": "application/yaml"},
                                                 "body": event.target.value                                             
                                                })
                                              .then(response => {if (response.ok){
                                                  response.text().then(data => {                                                  
                                                      setToken(data);
                                                      
                                                  })
                                              }})                                          
                                              .catch(error => {
                                                  console.log(error);
                                              }).finally(() => {
                                                  ;
                                              });
                                      });
                                  }
                              }/>
                </div>
                <div className="column">
                    <object crossOrigin="use-credentials" height="100%" width="100%" type="application/pdf" data={`/api/v1/pdfFromToken?token=${token}#zoom=85&scrollbar=0&toolbar=0&navpanes=0`}>
                        <p>Insert your error message here, if the PDF cannot be displayed.</p>
                    </object>
                </div>
            </div>
        </div>
    );
}

export default App;
