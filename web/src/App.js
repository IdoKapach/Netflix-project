import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate, useNavigate } from "react-router-dom";
import LoginPage from "./LoginPage";
import SignUpPage from "./SignUpPage";
import MainPage from "./MainPage";
import ManagePage from "./ManagePage";
import SearchPage from "./SearchPage";
import VideoPlayerPage from "./VideoPlayerPage";
import MainLoggedPage from "./MainLoggedPage";
import MovieInfoPage from "./MovieInfoPage"
import { LoggedBar, UnLoggedBar } from "./components/Bars";
import Bottom from "./components/Bottom";
import LightMode from "./components/LightMode";
import CategoryPage from "./CategoryPage"
import NoFoundPage from "./NoFoundPage";

const App = () => {
  // initiating token variable which indicates if user is logged in
  const [token, setToken] = useState(() => {
          const token = localStorage.getItem("token");
          return token ? token : null;
      })
  // initiating token variable which indicates if user a manager
  const [mToken, setMToken] = useState(() => {
          const mToken = localStorage.getItem("mToken");
          return mToken ? mToken : null;
      })
  // func that updates the token when the user login
  const handleLogin = (token) => {
    console.log("update token")
    setToken(token);
    localStorage.setItem("token", token)
  }
  // func that updates the mToken when manager login
  const handleMLogin = (token) => {
    setMToken(token);
    localStorage.setItem("mToken", token)
  }

  

  // render the router of the app
  return (
    <BrowserRouter>
    {/* render the lightMode button */}
      <LightMode setToken={handleLogin}/>
    {/* renders the relevant bar for logged/unlogged user */}
      {token ?
       <LoggedBar token={token} mToken={mToken} setMToken={setMToken} setToken={setToken} />
       : <UnLoggedBar />}
      <Routes>
        {/* renders the relevant main-page for logged/unlogged user */}
        <Route
         path="/"
         element= {
          token ? (
            <MainLoggedPage token={token} mToken={mToken} />
          ) : (
            <MainPage />
          )
        }/>
        {/* renders the login page */}
        <Route
          path="/login"
          element= {<LoginPage handleLogin={handleLogin} onMLogin={handleMLogin} />}
        />
        {/* renders the sign-up page */}
        <Route
          path="/sign-up"
          element= {<SignUpPage handleLogin={handleLogin} />}
        />
        {/* renders the relevant page for manager-manage page or not manager-login page */}
        <Route
          path="/manage"
          element= {
            mToken ?
            (<ManagePage mToken={mToken} />)
             :
            (<Navigate to="/login" />)
          }
        />
        {/* renders the relevant page for logged user-search page or not logged-login page */}
        <Route
          path="/search/:query"
          element= {
            token ?
            (<SearchPage />)
             :
            (<Navigate to="/login" />)
          }
        />
        {/* renders the relevant page for logged user-video-player page or not logged-login page */}
        <Route
          path="/movie-play/:movieId"
          element= {
            token ?
            (<VideoPlayerPage />)
             :
            (<Navigate to="/login" />)
          }
        />
        {/* renders the relevant page for logged user-movie-info page or not logged-login page */}
        <Route
          path="/movie-info/:movieId"
          element= {
            token ?
            (<MovieInfoPage token={token} />)
             :
            (<Navigate to="/login" />)
          }
        />
        {/* renders the relevant page for logged user-category page or not logged-login page */}
        <Route
          path="/category/:categoryId"
          element= {
            token ?
            (<CategoryPage token={token} />)
             :
            (<Navigate to="/login" />)
          }
        />
        {/* renders the no-found page in any ohher case*/}
        <Route
          path="/*"
          element= {<NoFoundPage />}
        /> 
      </Routes>
      <Bottom />
    </BrowserRouter>
  );
};

export default App