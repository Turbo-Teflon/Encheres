function togglePassword(id, iconId) {
    const input = document.getElementById(id);
    const icon = document.getElementById(iconId);

    if (input.type === "password") {
        input.type = "text";
        if (icon) icon.textContent = "🙈"; // œil barré
    } else {
        input.type = "password";
        if (icon) icon.textContent = "👁️"; // œil ouvert
    }
}
