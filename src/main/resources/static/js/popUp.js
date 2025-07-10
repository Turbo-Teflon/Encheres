function openConfirmationModal() {
  document.getElementById("confirmationModal").style.display = "flex";
  activerEyeSurPopupPassword(); 
}


function closeConfirmationModal() {
  document.getElementById("confirmationModal").style.display = "none";
}

window.onclick = function(event) {
  const modal = document.getElementById("confirmationModal");
  if (event.target === modal) {
    modal.style.display = "none";
  }
};

function activerEyeSurPopupPassword() {
  const input = document.getElementById("popupPassword");
  if (!input) return;

  const existingEye = input.parentElement.querySelector(".toggle-password");
  if (existingEye) return; // Ne rien faire si l'œil est déjà là

  const wrapper = document.createElement("div");
  wrapper.classList.add("password-wrapper");

  input.parentNode.replaceChild(wrapper, input);
  wrapper.appendChild(input);

  const button = document.createElement("span");
  button.classList.add("toggle-password");
  button.textContent = "🙈";

  button.addEventListener("click", () => {
    const isHidden = input.type === "password";
    input.type = isHidden ? "text" : "password";
    button.textContent = isHidden ? "👁️" : "🙈";
  });

  wrapper.appendChild(button);
}

