$(document).ready(function () {
    let fullChange = $('#fullChange');
    let noChangeInputs = [$('#userMail'), $('#userPass')];
    let logOutButton = $('#logOut');
    
    function fullChangeToggler() {
        if (this.checked) {
            for (let i = 0, l = noChangeInputs.length; i < l; i++) {
                $(noChangeInputs[i]).prop("readonly", false);
            }
        } else {
            for (let i = 0, l = noChangeInputs.length; i < l; i++) {
                $(noChangeInputs[i]).prop("readonly", true);
            }
        }
    }

// enable full change on user info edit
    $(fullChange).click(fullChangeToggler);
    // clear cart on logout
    $(logOutButton).click(G.cart.clear);
});