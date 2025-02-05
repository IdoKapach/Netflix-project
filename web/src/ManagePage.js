import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { WarningAlert, SuccessAlert } from "./components/Alerts";

function CategoryCreate({mToken}) {
    const [nError, setNError] = useState("")
    const [gError, setGError] = useState("")
    const [success, setSuccess] = useState()

    const [promoted, setPromoted] = useState(true)
    const [name, setName] = useState("")


    // func that's called by form in submit
    const handleSubmit = (e) => {
        e.preventDefault()

        let error = false
        // if one of the field is empty, raise alert about it        
        if (name === "") {
            setNError("Name box is empty")
            error = true
        }
        else {setNError("")}

        if (error) {
            setSuccess("")
            console.log("name is empty")
            return
        }

        // calling post api/categories
        console.log("success: ", promoted)
        setSuccess("The category created")

        setName("")
    }

    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Create</h3>
        <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
          <div class="form-floating mb-3">
            <input type="name" class="form-control" id="ccn" placeholder="name@example.com" fdprocessedid="t81uq" value={name} onChange={(e) => setName(e.target.value)} />
            <label for="ccn">Name</label>
            {nError && <WarningAlert message={nError} />}
          </div>
          <label htmlFor="dropdown">Promoted</label>
          <select
        id="dropdown"
        className="form-select"
        value={promoted}
        onChange={(e) => setPromoted(e.target.value)}
        style={{marginBottom: "5%"}}
      >
        <option value={true}>Yes</option>
        <option value={false}>No</option>
        Promoted
      </select>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Create Category</button>
        </form>
      </div>
    )
}

function CategoryUpdate({mToken}) {
    const [gError, setGError] = useState("")
    const [iError, setIError] = useState("")
    const [success, setSuccess] = useState()

    const [promoted, setPromoted] = useState("")
    const [name, setName] = useState("")
    const [id, setId] = useState("")

    // func that's called by form in submit
    const handleSubmit = (e) => {
        e.preventDefault()

        let error = false
        // if name and promoted are empty, or id is empty raise alert about it  
        if (id === "") {
            setIError("Id box is empty")
            error = true
        }
        else {setIError("")}      
        if (name === "" && promoted === "") {
            setGError("must specified at least one of name/promoted")
            error = true
        }
        else {setGError("")}

        if (error) {
            setSuccess("")
            console.log("fields error")
            return
        }

        // calling patch api/categories/:id



        console.log("success: ", promoted)
        setSuccess("The category updated")
        setId("")
        setName("")
        setPromoted("")
    }
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Update</h3>
        <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="cui" placeholder="name@example.com" fdprocessedid="t81uq" value={id} onChange={(e) => setId(e.target.value)}/>
            <label for="cui">Id</label>
            {iError && <WarningAlert message={iError} />}
          </div>
          <div class="form-floating mb-3">
            <input type="name" class="form-control" id="ccn" placeholder="name@example.com" fdprocessedid="t81uq" value={name} onChange={(e) => setName(e.target.value)}/>
            <label for="ccn">Name</label>
          </div>
          <label htmlFor="dropdown">Promoted</label>
          <select
        id="dropdown"
        className="form-select"
        value={promoted}
        onChange={(e) => setPromoted(e.target.value)}
        style={{marginBottom: "5%"}}
      >
        <option value="">Select an option</option>
        <option value={true}>Yes</option>
        <option value={false}>No</option>
        Promoted
      </select>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Update Category</button>
        </form>
      </div>
    )
}

function CateroryDelete({mToken}) {
    const [gError, setGError] = useState("")
    const [success, setSuccess] = useState()
    const [id, setId] = useState("")

    // func that's called by form in submit
    const handleSubmit = (e) => {
        e.preventDefault()

        let error = false
        // if id is empty raise alert about it  
        if (id === "") {
            setGError("Id box is empty")
            error = true
        }
        else {setGError("")}

        if (error) {
            setSuccess("")
            console.log("fields error")
            return
        }

        // calling delete api/categories/:id



        console.log("success")
        setSuccess("The category was deleted")
        setId("")
    }
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Delete</h3>
        <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
        {gError && <WarningAlert message={gError} />}
        {success && <SuccessAlert message={success} />}
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="cdi" placeholder="name@example.com" fdprocessedid="t81uq" value={id} onChange={(e) => setId(e.target.value)} />
            <label for="cdi">Id</label>
          </div>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Delete Category</button>
        </form>
      </div>
    )
}

function MovieCreate({mToken}) {
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Create</h3>
        <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary">
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="mci" placeholder="name@example.com" fdprocessedid="t81uq" />
            <label for="mci">Id</label>
          </div>
          <div class="form-floating mb-3">
            <input type="email" class="form-control" id="ficc" placeholder="name@example.com" fdprocessedid="t81uq" />
            <label for="ficc">Email address</label>
          </div>
          <div class="form-floating mb-3">
            <input type="password" class="form-control" id="fpcc" placeholder="Password" fdprocessedid="zmc0rc" />
            <label for="fpcc">Password</label>
          </div>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Sign up</button>
        </form>
      </div>
    )
}

function MovieUpdate({mToken}) {
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Update</h3>
        <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary">
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="mui" placeholder="name@example.com" fdprocessedid="t81uq" />
            <label for="mui">Id</label>
          </div>
          <div class="form-floating mb-3">
            <input type="email" class="form-control" id="ficu" placeholder="name@example.com" fdprocessedid="t81uq" />
            <label for="ficu">Email address</label>
          </div>
          <div class="form-floating mb-3">
            <input type="password" class="form-control" id="fpcu" placeholder="Password" fdprocessedid="zmc0rc" />
            <label for="fpcu">Password</label>
          </div>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Sign up</button>
        </form>
      </div>
    )
}

function MovieDelete({mToken}) {
    return (
        <div class="col-md-10 mx-auto col-lg-5">
        <h3>Delete</h3>
        <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary">
        <div class="form-floating mb-3">
            <input type="id" class="form-control" id="mdi" placeholder="name@example.com" fdprocessedid="t81uq" />
            <label for="mdi">Id</label>
          </div>
          <div class="form-floating mb-3">
            <input type="email" class="form-control" id="ficd" placeholder="name@example.com" fdprocessedid="t81uq" />
            <label for="ficd">Email address</label>
          </div>
          <div class="form-floating mb-3">
            <input type="password" class="form-control" id="fpcd" placeholder="Password" fdprocessedid="zmc0rc" />
            <label for="fpcd">Password</label>
          </div>
          <button class="w-100 btn btn-lg btn-primary" type="submit" fdprocessedid="lg6y7">Sign up</button>
        </form>
      </div>
    )
}
function ManagePage({mToken}) {
    return (<div>
<div class="container col-xl-10 col-xxl-8 px-4 py-5">
    <h1>Categories</h1>
    <div class="row align-items-center g-lg-5 py-5">

    <CategoryCreate mToken={mToken} />
    <CategoryUpdate mToken={mToken} />
    <CateroryDelete mToken={mToken} />
    </div>
  </div>
  <div class="container col-xl-10 col-xxl-8 px-4 py-5">
  <h1>Movies</h1>
  <div class="row align-items-center g-lg-5 py-5">

  <MovieCreate mToken={mToken} />
  <MovieUpdate mToken={mToken} />
  <MovieDelete mToken={mToken} />
  </div>
</div>
</div>
    )
}

export default ManagePage