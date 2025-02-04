// render warning alert
function WarningAlert({message}) {
    return (
      <div className="alert alert-warning d-flex align-items-center" role="alert">
        <svg class="d-none">
      <symbol id="exclamation-triangle-fill" viewBox="0 0 16 16">
        <path
          fill="currentColor"
          d="M8.982 1.566a1.13 1.13 0 00-1.964 0L.165 13.233c-.457.778.091 1.767.982 1.767h13.706c.89 0 1.438-.99.982-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 01-1.1 0L7.05 5.995C6.996 5.462 7.465 5 8 5zm.002 6a1 1 0 110 2 1 1 0 010-2z"
        />
      </symbol>
    </svg>
  <svg
    className="bi flex-shrink-0 me-2"
    role="img"
    aria-label="Warning:"
    width="16" 
    height="16" 
  >
    <use xlinkHref="#exclamation-triangle-fill" />
  </svg>
  <div>
    {message}
  </div>
</div>
    )
}

export {WarningAlert}