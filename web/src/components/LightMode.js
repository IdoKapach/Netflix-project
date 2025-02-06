import React, { useEffect, useState } from "react";
// render the light controller buttom
function LightMode() {
  // gets the initiate light state (theme)
    const [isLight, setIsLight] = useState(() => {
        const savedTheme = localStorage.getItem("theme");
        return savedTheme === "dark" ? false : true;
    });
  useEffect(() => {
    // add theme toggling logic
    const themeButtons = document.querySelectorAll("[data-bs-theme-value]");
    const body = document.body;
    const logo = document.querySelector("#bd-theme svg use")
    // setting the click effect for each of the light buttons
    themeButtons.forEach((button) => {
      button.addEventListener("click", () => {
        const theme = button.getAttribute("data-bs-theme-value");
        body.setAttribute("data-bs-theme", theme);
        localStorage.setItem("theme", theme)
        // setting the appropriate light mode and the logo according to the theme
        if (theme === "light") {
            logo.setAttribute("href", "#sun-fill")
            setIsLight(true)
        }
        else {
            logo.setAttribute("href", "#moon-stars-fill")
            setIsLight(false)
        }
        // update active button
        themeButtons.forEach((btn) => btn.classList.remove("active"));
        button.classList.add("active");
      });
    });
    return () => {
      // cleanup event listeners
      themeButtons.forEach((button) =>
        button.removeEventListener("click", () => {})
      );
    };
  }, [setIsLight]);

  const body = document.body;

    let theme;
    let icon;
    let lActine = ""
    let dActive = ""
    // setting the light mode according to the default state
    if (isLight) {
        body.setAttribute("data-bs-theme", "light");
        theme = "light"
        icon = "#sun-fill"
        lActine = " active"
    }
    else {
        body.setAttribute("data-bs-theme", "dark");
        theme = "dark"
        icon = "#moon-stars-fill"
        dActive = " active"
    }
  // render the lightControl button
  return (
    <div className="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle" style={{position: "absolute", zIndex: 9999}}>
      <button
        className="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center"
        id="bd-theme"
        type="button"
        aria-expanded="false"
        data-bs-toggle="dropdown"
        aria-label={`Toggle theme (${theme})`}
      >
        <svg className="bi my-1 theme-icon-active" width="1em" height="1em">
          <use href={icon}></use>
        </svg>
        <span className="visually-hidden" id="bd-theme-text">
          Toggle theme
        </span>
      </button>
      <ul
        className="dropdown-menu dropdown-menu-end shadow"
        aria-labelledby="bd-theme-text"
      >
        <li>
          <button
            type="button"
            className={`theme-b dropdown-item d-flex align-items-center${lActine}`}
            data-bs-theme-value="light"
            aria-pressed="true"
          >
            <svg className="bi me-2 opacity-50" width="1em" height="1em">
              <use href="#sun-fill"></use>
            </svg>
            Light
          </button>
        </li>
        <li>
          <button
            type="button"
            className={`theme-b dropdown-item d-flex align-items-center${dActive}`}
            data-bs-theme-value="dark"
            aria-pressed="false"
          >
            <svg className="bi me-2 opacity-50" width="1em" height="1em">
              <use href="#moon-stars-fill"></use>
            </svg>
            Dark
          </button>
        </li>
      </ul>
    </div>
  );
}


function LightMode1() {
    return (
    <div class="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle">
      <button class="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center" id="bd-theme" type="button" aria-expanded="false" data-bs-toggle="dropdown" aria-label="Toggle theme (light)" fdprocessedid="vtdivv">
        <svg class="bi my-1 theme-icon-active" width="1em" height="1em"><use href="#sun-fill"></use></svg>
        <span class="visually-hidden" id="bd-theme-text">Toggle theme</span>
      </button>
      <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="bd-theme-text" style={{}}>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center active" data-bs-theme-value="light" aria-pressed="true">
            <svg class="bi me-2 opacity-50" width="1em" height="1em"><use href="#sun-fill"></use></svg>
            Light
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="dark" aria-pressed="false">
            <svg class="bi me-2 opacity-50" width="1em" height="1em"><use href="#moon-stars-fill"></use></svg>
            Dark
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
      </ul>
    </div>
    )
}



export default LightMode