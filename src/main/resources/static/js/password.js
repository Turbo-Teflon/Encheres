document.addEventListener("DOMContentLoaded", function () {
    const fields = [
        { inputId: "motDePasse", iconId: "eye-icon1" },
        { inputId: "confirmation", iconId: "eye-icon2" }
    ];

    fields.forEach(field => {
        const input = document.getElementById(field.inputId);
        if (!input) return;

        // Création du conteneur
        const container = document.createElement("div");
        container.classList.add("password-container");

        // Création du bouton
        const button = document.createElement("span");
        button.classList.add("toggle-password");
        button.id = field.iconId;
        button.textContent = "👁️";

        // Fonction toggle
        button.addEventListener("click", () => {
            if (input.type === "password") {
                input.type = "text";
                button.textContent = "🙈";
            } else {
                input.type = "password";
                button.textContent = "👁️";
            }
        });

        // Remplacer l'input par le container (et y réintégrer l'input et le bouton)
        input.parentNode.replaceChild(container, input);
        container.appendChild(input);
        container.appendChild(button);
    });
});
