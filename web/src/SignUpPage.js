// render the sign-up page
function SignUpPage({handleLogin}) {
    
  return (
      <div class="d-flex align-items-center py-4 bg-body-tertiary" id="login-cart">
  
<main class="form-signin w-100 m-auto">
<form>
  <h1 class="h3 mb-3 fw-normal">Sign up</h1>

  <div class="form-floating">
    <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" fdprocessedid="k2b4s"/>
    <label for="floatingInput">Username</label>
  </div>
  <div class="form-floating">
    <input type="password" class="form-control" id="floatingPassword" placeholder="Password" fdprocessedid="6hoglo"/>
    <label for="floatingPassword">Password</label>
  </div>
  <div class="form-floating">
    <input type="path" class="form-control" id="floatingImgPath" placeholder="name@example.com" fdprocessedid="k1b4s"/>
    <label for="floatingInput">Image-path</label>
  </div>

  <button class="btn btn-outline-primary w-100 py-2" type="submit" fdprocessedid="tv6em" style={{"margin-top": "8%"}}>Sign in</button>
</form>
</main>
  

<span id="PING_IFRAME_FORM_DETECTION" style={{display: null}}></span></div>
  ) 
}

export default SignUpPage