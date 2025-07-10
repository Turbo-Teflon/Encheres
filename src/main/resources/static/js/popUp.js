function openConfirmationModal() {
  document.getElementById("confirmationModal").style.display = "block";
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
