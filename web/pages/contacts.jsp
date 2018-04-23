<section id="block_contacts">
    <h1 id="contact">Contact us</h1>
    <p style="text-align: center">Working hours:<br>Monday - Friday: 9:00 - 18:00<br>Weekends: closed<br><span id="phone">Phone number: <a href="tel:+34 649 26 65 86">+34 649 26 65 86</a></span> <span id="fb"><a href="https://www.facebook.com/zuck" target="_blank">Facebook</a></span></p>
    <div id="map">My map will go here</div>
    <script>
        function myMap() {
            var company = {lat: 39.512729, lng: -0.424220};
            var mapProp = {
                center: company,
                zoom: 10
            };
            var map = new google.maps.Map(document.getElementById("map"), mapProp);
            var marker = new google.maps.Marker({
                position: company,
                map: map
            });
        }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD1TjNfdDlKLWVFBdPn6e6K28468sd_xso&callback=myMap"></script>
</section>
