document.addEventListener("DOMContentLoaded", function () {
	const fields = [
	  { inputId: "motDePasse", iconId: "eye-icon1" },
	  { inputId: "nouveauMotDePasse", iconId: "eye-icon2" },
	  { inputId: "confirmation", iconId: "eye-icon3" }
	];

  fields.forEach(field => {
    const input = document.getElementById(field.inputId);
    if (!input) return;

    // Création du wrapper
    const wrapper = document.createElement("div");
    wrapper.classList.add("password-wrapper");

    // Remplacement de l'input par le wrapper dans le DOM
    input.parentNode.replaceChild(wrapper, input);
    wrapper.appendChild(input);

    // Création et ajout du bouton œil
    const button = document.createElement("span");
    button.classList.add("toggle-password");
    button.id = field.iconId;
    button.textContent = "🙈";

    button.addEventListener("click", () => {
      const isHidden = input.type === "password";
      input.type = isHidden ? "text" : "password";
      button.textContent = isHidden ? "👁️" : "🙈";
    });

    wrapper.appendChild(button);
  });
});
