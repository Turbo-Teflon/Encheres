document.addEventListener("DOMContentLoaded", function () {
    const fields = [
        { inputId: "motDePasse", iconId: "eye-icon1" },
        { inputId: "confirmation", iconId: "eye-icon2" }
    ];

    fields.forEach(field => {
        const input = document.getElementById(field.inputId);
        if (!input) return;

        const wrapper = document.createElement("div");
        wrapper.classList.add("password-container");

        // Insérer le wrapper AVANT l'input
        input.parentNode.insertBefore(wrapper, input);

        // Déplacer l'input dedans
        wrapper.appendChild(input);

        // Ajouter le bouton dans le wrapper
        const button = document.createElement("span");
        button.classList.add("toggle-password");
        button.id = field.iconId;
        button.textContent = "🙈";

        button.addEventListener("click", () => {
            if (input.type === "password") {
                input.type = "text";
                button.textContent = "👁️";
            } else {
                input.type = "password";
                button.textContent = "🙈";
            }
        });

        wrapper.appendChild(button);
    });
});

