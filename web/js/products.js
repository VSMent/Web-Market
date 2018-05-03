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
        let inPack = +$(this).parent().prev().prev().prev().prev().children().text();

        let prodId;
        for (let i = 0; i < buttonsAddToCart.length; i++) {
            if (this == buttonsAddToCart[i]) {
                prodId = $(buttonsAddToCart[i]).attr('_product');
                break;
            }
        }

        if (prodId && amount != 0) {
            name = productNameNodes[parseInt((prodId - 1) / 3)].innerHTML +" x" + inPack;
            G.cart.addProduct(prodId, price, amount, name);

            productTotalAmount.text(G.cart.price + "$");

            $(this).parent().prev().children().val(0);

            $(messageBox).text(`${name}(${price}$) * ${amount} = ${amount * price}$ added to cart`);
            $(messageBox).fadeIn().delay(2000).fadeOut();
        }
    }
});