$(document).ready(function () {
    let productNameNodes = $('.productName');
    let messageBox = $('#message');
    let productTotalAmount = $('#productTotalAmount');	// Product total amount span
    let buttonsAddToCart = $('.addToCart');

// add products to cart buttons
    for (let i = 0, l = buttonsAddToCart.length; i < l; i++) {
        $(buttonsAddToCart[i]).click(addToCart);
    }

    $(productTotalAmount).text(G.cart.price + "$");

    function addToCart() {
        let name;
        let price = +$(this).parent().prev().prev().prev().children().text();
        let amount = +$(this).parent().prev().children().val();

        let prodId;
        for (let i = 0; i < buttonsAddToCart.length; i++) {
            if (this == buttonsAddToCart[i]) {
                prodId = i + 1;
                break;
            }
        }

        if (prodId && amount != 0) {
            G.cart.addProduct(prodId, price, amount);
            name = productNameNodes[parseInt((prodId - 1) / 3)];

            productTotalAmount.text(G.cart.price + "$");

            $(this).parent().prev().children().val(0);

            $(messageBox).text(`${name.innerHTML} (${price}$) * ${amount} = ${amount * price}$ added to cart`);
            $(messageBox).fadeIn().delay(2000).fadeOut();
        }
    }
});