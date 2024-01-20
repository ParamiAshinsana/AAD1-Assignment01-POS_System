$('#loginButton').click(function () {
//Get the entered username and password
        var userName = $('#username').val();
        var password = $('#password').val();

        // Perform an AJAX request to verify the credentials
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/Demo_war_exploded/login", // Update the URL to match your servlet endpoint
            contentType: "application/json",
            data: JSON.stringify({ "userNameQ": userName, "passwordQ": password }),
            success: function (response) {
                console.log(response)
                if (response.status === "200") {
                    // Swal.fire({
                    //     position: "center",
                    //     icon: "success",
                    //     title: "Successfully Login ",
                    //     showConfirmButton: false,
                    //     timer: 2300
                    // });
                    Swal.fire({
                        title: "Successfully Login ✨",
                        width: 600,
                        padding: "3em",
                        color: "#ffcc69",
                        background: "#fff url(../style/imgs/VTEX.png)"
                    });



                    // If authentication is successful, redirect to index.html
                    // window.location.href = "index.html";

                    setTimeout(function () {
                        // If authentication is successful, redirect to index.html
                        window.location.href = "index.html";
                    }, 2800);



                } else {
                    // If authentication fails, display an error message or handle accordingly
                    alert("Invalid username or password");
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Invalid UserInputs !",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            },
            error: function () {
                // Handle AJAX error if needed
                alert("Error during authentication");
            }
        });
})