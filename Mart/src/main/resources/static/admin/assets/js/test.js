document.addEventListener("DOMContentLoaded", function() {
    // Lưu URL hiện tại vào localStorage
    var currentUrl = window.location.pathname;
    localStorage.setItem('activeLink', currentUrl);

    // Khôi phục trạng thái active từ localStorage
    var activeLink = localStorage.getItem('activeLink');
    if (activeLink) {
        var linkElement = document.querySelector('a[href="' + activeLink + '"]');
        if (linkElement) {
            linkElement.classList.add('active');
            var parentLi = linkElement.closest('li');
            if (parentLi) {
                parentLi.classList.add('active');
            }
            var parentCollapse = linkElement.closest('.collapse');
            if (parentCollapse) {
                parentCollapse.classList.add('show');
                var navItem = parentCollapse.closest('.nav-item');
                if (navItem) {
                    navItem.classList.add('active');
                }
            }
        }
    }

    // Lưu trạng thái active khi click vào link
    var navLinks = document.querySelectorAll('.nav-collapse a');
    navLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            localStorage.setItem('activeLink', this.getAttribute('href'));
        });
    });
});
document.addEventListener('DOMContentLoaded', function() {
    var imageElement = document.getElementById('image');
    if (imageElement) {
        imageElement.addEventListener('change', function(e) {
            const img = document.getElementById('reviewImage');
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    img.style.display = 'block';
                    img.src = e.target.result;
                }
                reader.readAsDataURL(file);
            } else {
                img.style.display = 'none';
            }
        });
    }
});
document.addEventListener('DOMContentLoaded', function() {
    const img = document.getElementById('reviewImage');
    if (!img.src || img.src.trim() === "") {
        img.style.display = 'none';
    } else {
        img.style.display = 'block';
    }
});