import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getCategories } from "../fetchRequests";


// render button that's logs the user out and navigate to /login 
function LogoutButton({ setToken, setMToken, handleLogout }) {
  const navigate = useNavigate()
  // func that updates the token when the user logout
  // const handleLogout = () => {
  //   console.log("inside");

  //   // clear tokens from state
  //   setToken(null);
  //   setMToken(null)

  //   // remove tokens localStorage
  //   localStorage.removeItem("token"); 
  //   localStorage.removeItem("mToken");

  //   // navigate to login
  //   navigate("/login")
  // }
  // return the button
  return <button class="nav-link active" aria-current="page" onClick={handleLogout}>Logout</button>
}

// render the managment button forr managers user
const Managment = ({mToken}) => {
    return mToken ?
      (<li className="nav-item">
        <a className="nav-link" href="/manage">Managment</a>
     </li>) : (null)
}

// render search form that navigates to search/:query in submit when query is the text inn the input
function SearchForm() {
  const [query, setQuery] = useState("")
  const navigate = useNavigate()

  // func that's operated in submit
  const handleSubmit = (e) => {
    e.preventDefault()
    if (query.trim() !== "") {
      // navigates to /search/:query
      navigate(`/search/${query}`)
    }
    setQuery("")
  };

  return (
    <form className="d-flex" role="search" onSubmit={handleSubmit}>
      <input
        className="form-control me-2"
        type="search"
        placeholder="Search"
        aria-label="Search"
        value={query}
        onChange={(e) => setQuery(e.target.value)}  // updates queryon change
      />
      <button className="btn btn-outline-success" type="submit">Search</button>
    </form>
  );
}

const Categories = ({token}) => {
  // call api/categories ex3 in order to get all the categories
  // let categories=[
  //   {"_id": 1, "name":"Action"}, {"_id": 2, "name":"Drama"}, {"_id": 3, "name":"Comedy"}, {"_id": 4, "name":"Horror"}
  // ]

  const [categories, setCategories] = useState([])
  useEffect(() => {
    const fetchCategories = async () => {
        const data = await getCategories(token);
        setCategories(data);
    };

    fetchCategories();
}, [token]);


  return (
    <ul class="dropdown-menu">
      {categories.map((category) => <li><a class="dropdown-item" href={`/category/${category._id}`}>{category.name}</a></li>)}
    </ul>
  )
}
// render the bar for logged user
function LoggedBar({token, mToken, nickname, profileImg, setMToken, setToken, handleLogout}) {
    // get user's name and image by api/users/:_id
    let userName = nickname
    // let userImage = await getImg(token, `http://localhost:3000/media/${profileImg}`)



    return (<nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
        <div class="container-fluid">
          <a class="navbar-brand" href="/" >Netflix</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
              <li style={{paddingTop: "3%"}}>
                <h6 id="user-bar">Hello, {userName}!</h6>
              </li>
              <li id="circle-img">
                <img src={`http://localhost:3000/media/${profileImg}?token=${token}`} alt="profile" width="36" height="36" class="rounded-circle" />
              </li>
              <Managment mToken={mToken}/>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Categories
                </a>
                <Categories token={token}/>
              </li>
              <li class="nav-item">
                <LogoutButton setMToken={setMToken} setToken={setToken} handleLogout={handleLogout}/>
              </li>
            </ul>
          </div>
          <SearchForm />
        </div>
      </nav>)
}

// render button that navigates to login page
function LoginButton() {
  const navigate = useNavigate()
  const toLogin = () => {navigate("/login")}
  return <button type="button" class="btn btn-outline-primary me-2" onClick={toLogin}>Login</button>
}

// render button that navigates to sign-up page
function SignUpButton() {
  const navigate = useNavigate()
  const toSignUp = () => {navigate("/sign-up")}
  return <button type="button" class="btn btn-primary" onClick={toSignUp}>Sign-up</button>
}

// render the br for the nlogged users
function UnLoggedBar() {
    return (<nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
        <div class="container-fluid">
        <a class="navbar-brand" href="/" >Netflix</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          {/* <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link" href="#">Features</a>
              </li>
            </ul>
          </div> */}
          <div class="text-end">
          <LoginButton />
          <SignUpButton />
        </div>
        </div>
      </nav>)
}

export {LoggedBar, UnLoggedBar}