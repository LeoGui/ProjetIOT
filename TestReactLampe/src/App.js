import React, { Component } from 'react';
import lampe1 from './lampe1.png';
import lampe2 from './lampe2.png';
import lampe3 from './lampe3.png';
import lampe4 from './lampe4.png';
import start from './start.png';
import stop from './stop.png';

import Button from './Button';

import './App.css';

class App extends React.Component {
  render() {
    return (
      <div>
        <header className="App-header">
          <img src={lampe2} className="App" alt="logo" />
          <h1 className="App-title">Welcome to LightController App</h1>
        </header>
        <p className="App-intro">
          Turn on the light
        </p>

        <p className="App">
          <img src ={lampe1} className="lampe1" alt="lampe1"/>
          <img src ={lampe2} className="lampe2" alt="lampe1"/>
          <img src ={lampe3} className="lampe3" alt="lampe1"/>
          <img src ={lampe4} className="lampe4" alt="lampe1"/>

        </p>

        <p className='App-intro'>
        Start scrolling
        </p>

        <p className="App">
          <img src ={start} className="lampe2" alt="lampe2"/>
          <img src ={stop} className="lampe2" alt="lampe2"/>
        </p>

       

      </div>


    );

    }
  }

export default App;
