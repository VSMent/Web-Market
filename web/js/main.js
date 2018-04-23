var G = {};
//$(document).ready(function () {
    G.menu = $('#menu');
    G.navButtons = $(menu).children('li');
    G.blocks = $('section');
    G.fullChange = $('#fullChange');
    G.noChangeInputs = [$('#userMail'), $('#userPass')];
    G.buttonsAddToCart = $('.addToCart');
    G.productNameNodes = $('.productName');
    G.messageBox = $('#message');
    G.cartList = $('#cartList');
    G.productTotalAmount = $('#productTotalAmount');	// Product total amount span
    G.cartTotalAmount = $('#cartTotalAmount');	// Cart total amount span


    G.cart = {
        "prodA": 0,
        "price": 0,
        "items": [],
        addProduct(id, price, amount) {
            cart.price = 0;
            cart.prodA = 0;

            let l = cart.items.length;

            if (l > 0) {
                for (let i = 0; i < l; i++) {
                    if (cart.items[i].id == id) {
                        cart.items[i].amount += amount;
                        break;
                    } else if (i == l - 1) {
                        cart.items.push(new Product(id, price, amount));
                    }
                }
            } else {
                cart.items.push(new Product(id, price, amount));
            }

            for (let i = 0; i < cart.items.length; i++) {
                cart.price += cart.items[i].price * cart.items[i].amount;
                cart.prodA += cart.items[i].amount;
            }
        },
        deleteProduct(id) {
            cart.price = 0;
            cart.prodA = 0;

            this.items.splice(id, 1);

            for (let i = 0; i < cart.items.length; i++) {
                cart.price += cart.items[i].price * cart.items[i].amount;
                cart.prodA += cart.items[i].amount;
            }
        }
    };

    G.Product = class Product {
        constructor(id, price, amount) {
            this.id = id;
            this.price = price;
            this.amount = amount;
        }
    }

    (function init() {
        // check url to show appropriate page
        G.hashHandler();

        // enable button clicks
        for (let i = 0, l = navButtons.length; i < l; i++) {
            $(G.navButtons[i]).click(G.navButtonHandler);
        }

        // enable full change on user info edit
        $(G.fullChange).click(G.fullChangeToggler);

        // enable back / forward buttons
        window.onhashchange = G.hashHandler;

        // add products to cart buttons
        for (let i = 0, l = G.buttonsAddToCart.length; i < l; i++) {
            $(G.buttonsAddToCart[i]).click(G.addToCart);
        }
    })();


    // website browsing
    G.navButtonHandler = function() {
        // set block in url
        let prevButtonId = location.hash;
        if (!prevButtonId) 
            $("#main").removeClass('active');
        else
            $(prevButtonId).removeClass('active');

        location.hash = $(this).attr('id');
        $(this).addClass('active');
    }

    G.arrayToggle = function(mode) {
        switch (mode) {
            case 2:
                // hide all blocks
                for (let i = 0, l = G.blocks.length; i < l; i++) {
                    $(G.blocks).hide();
                }
                break;
            case 3:
                // set user mail and pass fields unchangable
                for (let i = 0, l = G.noChangeInputs.length; i < l; i++) {
                    $(G.noChangeInputs[i]).prop("readonly", true);
                }
                break;
            case 4:
                // set user mail and pass fields changable
                for (let i = 0, l = G.noChangeInputs.length; i < l; i++) {
                    $(G.noChangeInputs[i]).prop("readonly", false);
                }
                break;
        }
    }

    G.showBlock = function(id) {
        G.arrayToggle(2);		// hide all blocks
        // show block based on button id
        switch (id) {
            case "#about":
                document.title = "About our company";
                $(G.blocks[1]).toggle();
                break;
            case "#contacts":
                document.title = "Our contacts";
                $(G.blocks[2]).toggle();
                break;
            case "#products":
                document.title = "Our products";
                $(G.blocks[3]).toggle();
                break;
            case "#cart":
                document.title = "Your cart";
//                if (G.isLoggedIn()) {
//                    $(G.blocks[8]).toggle();
//                } else {
                $(G.blocks[4]).toggle();
                G.loadCart();
//                }
                break;
            case "#user":
                document.title = "User";
//                if (G.isLoggedIn()) {
//                    $(G.blocks[7]).toggle();
//                } else {
                $(G.blocks[5]).toggle();
//                }
                break;
            case "#admin":
                document.title = "Admin panel";
                $(G.blocks[6]).toggle();
                break;
            default:
                document.title = "Company main page";
                $(G.blocks[0]).toggle();
        }
    }

    G.isLoggedIn = function() {	// check user login
//        return 0;	// 0 - not logged in
        return 1;	// 1 - logged in
//        return 2;	// 2 - logged in as admin
    }

    G.fullChangeToggler = function() {
        if (this.checked) {
            G.arrayToggle(4);
        } else {
            G.arrayToggle(3);
        }
    }

    G.hashHandler = function() {
        G.showBlock(location.hash);
        $(location.hash).addClass('active');
        if (!location.hash) {
            $("#main").addClass('active');

        }
        // always stay on top (othervise will scroll to block begining)
        window.scrollTo(0, 0);
    }

    G.findButton = function(buttonId) {
        for (let i = 0, l = G.navButtons.length; i < l; i++) {
            if ($(G.navButtons[i]).attr('id') == buttonId)
                return G.navButtons[i];
        }
        return null;
    }


    // cart 
    G.addToCart = function() {
        let name;
        let price = +$(this).parent().prev().prev().children().text();
        let amount = +$(this).parent().prev().children().val();


        let prodId;
        for (let i = 0; i < G.buttonsAddToCart.length; i++) {
            if (this == G.buttonsAddToCart[i]) {
                prodId = i + 1;
                break;
            }
        }

        if (prodId && amount != 0) {
            G.cart.addProduct(prodId, price, amount);
            name = G.productNameNodes[parseInt((prodId - 1) / 3)];

            G.productTotalAmount.text(cart.price + "$");

            $(this).parent().prev().children().val(0);

            $(G.messageBox).text(`${name.innerHTML} (${price}$) * ${amount} = ${amount * price}$ added to cart`);
            $(G.messageBox).fadeIn().delay(2000).fadeOut();
        }
    }

    G.loadCart = function() {
        $(G.cartList).empty();
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
//                $(name).text(` Name `);
                $(price).text(` (${G.cart.items[i].price})$ `);
                $(amount).text(`* ${G.cart.items[i].amount} `);
                $(fullPrice).text(`= ${G.cart.items[i].amount * cart.items[i].price}`);

                $(clear).text("Delete");
                $(clear).click(function () {
                    let itemIndex;
                    for (let j = 0, jl = G.cart.items.length; j < jl; j++) {
                        if (G.cart.items[j].id == id.innerHTML)
                            itemIndex = j;
                    }
                    G.cart.deleteProduct(itemIndex);
                    $(G.cartList).children()[itemIndex].remove();

                    $(G.productTotalAmount).text(G.cart.price + "$");
                    $(G.cartTotalAmount).text(G.cart.price + "$");
                });

                $(li).append(clear)/*.append(name)*/.append(price).append(amount).append(fullPrice).append(id);
                $(fragment).append(li);
            }
            $(G.cartTotalAmount).text(cart.price + "$");
            $(G.cartList).append(fragment);
        }
    }

//});



// DONE on load we need to check 'hash' and set apropriate block
// DONE if hash changes outside the script we need to change block