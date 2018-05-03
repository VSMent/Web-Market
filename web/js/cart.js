$(document).ready(function () {
    let cartList = $('#cartList');
    let cartTotalAmount = $('#cartTotalAmount');	// Cart total amount span
    let cartInput = $('#cartInput');
    
    loadCart();
    
    cartInput.val(cartInput.val()+JSON.stringify(G.cart.items));

    function loadCart() {
        $(cartList).empty();
        if (G.cart.items.length) {
            let fragment = document.createDocumentFragment();

            for (let i = 0, l = G.cart.items.length; i < l; i++) {
                let li = document.createElement('li');
                let id = document.createElement('p');
                let name = document.createElement('p');
                let price = document.createElement('p');
                let amount = document.createElement('p');
                let fullPrice = document.createElement('p');
                let clear = document.createElement('p');

                $(id).text(G.cart.items[i].id);
                $(name).text(` ${G.cart.items[i].name}`);
                $(price).text(`(${G.cart.items[i].price})$ `);
                $(amount).text(`* ${G.cart.items[i].amount} `);
                $(fullPrice).text(`= ${G.cart.items[i].amount * G.cart.items[i].price}`);

                $(clear).text("Delete");
                $(clear).click(function () {
                    let itemIndex;
                    for (let j = 0, jl = G.cart.items.length; j < jl; j++) {
                        if (G.cart.items[j].id == id.innerHTML)
                            itemIndex = j;
                    }
                    G.cart.deleteProduct(itemIndex);
                    $(cartList).children()[itemIndex].remove();

                    $(cartTotalAmount).text(G.cart.price + "$");
                });

                $(li).append(clear).append(name).append(price).append(amount).append(fullPrice).append(id);
                $(fragment).append(li);
            }
            $(cartTotalAmount).text(G.cart.price + "$");
            $(cartList).append(fragment);
        }
    }
});