var G = {};
$(document).ready(function () {
    navButtons = $($('#menu')).children('li');

    G.cart = {
        "amount": 0,
        "price": 0,
        "items": [],
        addProduct(productId, productPrice, productAmount, productName) {
            this.price = 0;
            this.amount = 0;

            let l = this.items.length;

            if (l > 0) {
                for (let i = 0; i < l; i++) {
                    if (this.items[i].id == productId) {
                        this.items[i].amount += productAmount;
                        break;
                    } else if (i == l - 1) {
                        this.items.push(new G.Product(productId, productPrice, productAmount, productName));
                    }
                }
            } else {
                this.items.push(new G.Product(productId, productPrice, productAmount, productName));
            }

            for (let i = 0; i < this.items.length; i++) {
                this.price += this.items[i].price * this.items[i].amount;
                this.amount += this.items[i].amount;
            }
        },
        deleteProduct(id) {
            this.price = 0;
            this.amount = 0;

            this.items.splice(id, 1);

            for (let i = 0; i < this.items.length; i++) {
                this.price += this.items[i].price * this.items[i].amount;
                this.amount += this.items[i].amount;
            }
        },
        clear() {
            this.amount = 0;
            this.price = 0;
            this.items = [];
            console.log(G.cart);
        }
    };

    G.Product = function Product(id, price, amount, name) {
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.name = name;
    };

// website browsing
    function navButtonHandler() {
        location.hash = $(this).attr('id');
    }

    function showBlock(id) {
        // show block based on button id
        switch (id) {
            case "#about":
                document.title = "About our company";
                Cargar("pages/about.jsp", "content");
                break;
            case "#contacts":
                document.title = "Our contacts";
                Cargar("pages/contacts.jsp", "content");
                break;
            case "#products":
                document.title = "Our products";
                Cargar("pages/products.jsp", "content");
                break;
            case "#cart":
                document.title = "Your cart";
                Cargar("pages/cart.jsp", "content");
                break;
            case "#user":
                document.title = "User";
                Cargar("pages/user.jsp", "content");
                break;
            case "#admin":
                document.title = "Admin panel";
                Cargar("pages/admin.jsp", "content");
                break;
            default:
                document.title = "Company main page";
                Cargar("pages/main.jsp", "content");
        }
    }

    function hashHandler() {
        if (G.hash != location.hash) {
            if (G.hash) {
                $(G.hash).removeClass('active');
            }else{
                $('#main').removeClass('active');
            }
            G.hash = location.hash;
            $(G.hash).addClass('active');
        }

        showBlock(location.hash);
        $(location.hash).addClass('active');
        if (!location.hash) {
            $("#main").addClass('active');

        }
        // always stay on top (othervise will scroll to block begining)
        window.scrollTo(0, 0);
    }

// cart 

    (function init() {
        // check url to show appropriate page
        hashHandler();

        // enable nav button clicks
        for (let i = 0, l = navButtons.length; i < l; i++) {
            $(navButtons[i]).click(navButtonHandler);
        }

        // enable back / forward buttons
        window.onhashchange = hashHandler;

    })();
});



// DONE on load we need to check 'hash' and set apropriate block
// DONE if hash changes outside the script we need to change block