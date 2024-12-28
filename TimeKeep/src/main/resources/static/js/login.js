document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const formData = new FormData(form);

        const formBody = new URLSearchParams();
        formData.forEach((value, key) => {
            formBody.append(key, value.toString());
        });

        fetch(form.action, {
            method: "POST",
            body: formBody.toString(),
            headers: { "Content-Type": "application/x-www-form-urlencoded" }
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Login failed");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Server response:", data);
                if (data.redirectUrl) {
                    console.log("Redirecting to:", data.redirectUrl);
                    window.location.href = data.redirectUrl;
                } else {
                    alert("Unexpected server response");
                }
            })
            .catch((error) => {
                console.error("Error during login:", error);
                alert("Login failed");
            });
    });
});