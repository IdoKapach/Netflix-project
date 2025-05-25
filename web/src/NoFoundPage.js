// render the no found page
function NoFoundPage() {
    let logoNum = Math.floor(Math.random() * 4);
    return (
        <div>
            <h1 class="h1 mb-1 fw-normal my-h1" style={{textAlign: "center", margin: "2% 5%", paddingBottom: "20px"}}>Page wasn't found</h1>
            <img class="img-center" src={`/404-logo/404-${logoNum}.png`} alt="404 error" />
        </div>
    )
}

export default NoFoundPage