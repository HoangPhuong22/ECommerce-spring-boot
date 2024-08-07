const q = document.querySelector.bind(document);
const qa = document.querySelectorAll.bind(document);

/**
 * Hàm tải template
 *
 * Cách dùng:
 * <div id="parent"></div>
 * <script>
 *  load("#parent", "./path-to-template.html");
 * </script>
 */
function load(selector, path) {
    const cached = localStorage.getItem(path);
    if (cached) {
        q(selector).innerHTML = cached;
    }

    fetch(path)
        .then((res) => res.text())
        .then((html) => {
            if (html !== cached) {
                q(selector).innerHTML = html;
                localStorage.setItem(path, html);
            }
        })
        .finally(() => {
            window.dispatchEvent(new Event("load"));
        });
}

/**
 * Hàm kiểm tra một phần tử
 * có bị ẩn bởi display: none không
 */
function isHidden(element) {
    if (!element) return true;

    if (window.getComputedStyle(element).display === "none") {
        return true;
    }

    let parent = element.parentElement;
    while (parent) {
        if (window.getComputedStyle(parent).display === "none") {
            return true;
        }
        parent = parent.parentElement;
    }

    return false;
}

/**
 * Hàm buộc một hành động phải đợi
 * sau một khoảng thời gian mới được thực thi
 */
function debounce(func, timeout = 300) {
    let timer;
    return (...args) => {
        clearTimeout(timer);
        timer = setTimeout(() => {
            func.apply(this, args);
        }, timeout);
    };
}

/**
 * Hàm tính toán vị trí arrow cho dropdown
 *
 * Cách dùng:
 * 1. Thêm class "js-dropdown-list" vào thẻ ul cấp 1
 * 2. CSS "left" cho arrow qua biến "--arrow-left-pos"
 */
const calArrowPos = debounce(() => {
    if (isHidden(q(".js-dropdown-list"))) return;

    const items = qa(".js-dropdown-list > li");

    items.forEach((item) => {
        const arrowPos = item.offsetLeft + item.offsetWidth / 2;
        item.style.setProperty("--arrow-left-pos", `${arrowPos}px`);
    });
});

// Tính toán lại vị trí arrow khi resize trình duyệt
window.addEventListener("resize", calArrowPos);

// Tính toán lại vị trí arrow sau khi tải template
window.addEventListener("load", calArrowPos);

/**
 * Giữ active menu khi hover
 *
 * Cách dùng:
 * 1. Thêm class "js-menu-list" vào thẻ ul menu chính
 * 2. Thêm class "js-dropdown" vào class "dropdown" hiện tại
 *  nếu muốn reset lại item active khi ẩn menu
 */
window.addEventListener("load", handleActiveMenu);

function handleActiveMenu() {
    const dropdowns = qa(".js-dropdown");
    const menus = qa(".js-menu-list");
    const activeClass = "menu-column__item--active";
    const removeActive = (menu) => {
        menu.querySelector(`.${activeClass}`)?.classList.remove(activeClass);
    };

    const init = () => {
        menus.forEach((menu) => {
            const items = menu.children;
            if (!items.length) return;

            removeActive(menu);
            if (window.innerWidth > 991) items[0].classList.add(activeClass);

            Array.from(items).forEach((item) => {
                item.onmouseenter = () => {
                    if (window.innerWidth <= 991) return;
                    removeActive(menu);
                    item.classList.add(activeClass);
                };
                item.onclick = () => {
                    if (window.innerWidth > 991) return;
                    removeActive(menu);
                    item.classList.add(activeClass);
                    item.scrollIntoView();
                };
            });
        });
    };

    init();

    dropdowns.forEach((dropdown) => {
        dropdown.onmouseleave = () => init();
    });
}

/**
 * JS toggle
 *
 * Cách dùng:
 * <button class="js-toggle" toggle-target="#box">Click</button>
 * <div id="box">Content show/hide</div>
 */
window.addEventListener("load", initJsToggle);

function initJsToggle() {
    qa(".js-toggle").forEach((button) => {
        const target = button.getAttribute("toggle-target");
        if (!target) {
            document.body.innerText = `Cần thêm toggle-target cho: ${button.outerHTML}`;
        }
        button.onclick = (e) => {
            e.preventDefault();

            if (!q(target)) {
                return (document.body.innerText = `Không tìm thấy phần tử "${target}"`);
            }
            const isHidden = q(target).classList.contains("hide");

            requestAnimationFrame(() => {
                q(target).classList.toggle("hide", !isHidden);
                q(target).classList.toggle("show", isHidden);
            });
        };
        document.onclick = function (e) {
            if (!e.target.closest(target)) {
                const isHidden = q(target).classList.contains("hide");
                if (!isHidden) {
                    button.click();
                }
            }
        };
    });
}

window.addEventListener("load", () => {
    const links = qa(".js-dropdown-list > li > a");

    links.forEach((link) => {
        link.onclick = () => {
            if (window.innerWidth > 991) return;
            const item = link.closest("li");
            item.classList.toggle("navbar__item--active");
        };
    });
});

window.addEventListener("load", () => {
    const tabsSelector = "prod-tab__item";
    const contentsSelector = "prod-tab__content";

    const tabActive = `${tabsSelector}--current`;
    const contentActive = `${contentsSelector}--current`;

    const tabContainers = qa(".js-tabs");
    tabContainers.forEach((tabContainer) => {
        const tabs = tabContainer.querySelectorAll(`.${tabsSelector}`);
        const contents = tabContainer.querySelectorAll(`.${contentsSelector}`);
        tabs.forEach((tab, index) => {
            tab.onclick = () => {
                tabContainer.querySelector(`.${tabActive}`)?.classList.remove(tabActive);
                tabContainer.querySelector(`.${contentActive}`)?.classList.remove(contentActive);
                tab.classList.add(tabActive);
                contents[index].classList.add(contentActive);
            };
        });
    });
});

// Slide show
var slideIndex = 0;
document.addEventListener("DOMContentLoaded", function () {
    showDivs(slideIndex);
    setInterval(function () {
        plusDivs(1);
    }, 5000);
});

function plusDivs(n) {
    showDivs(slideIndex += n);
}

function currentDiv(n) {
    showDivs(slideIndex = n);
}

function showDivs(n) {
    var i;
    var x = document.getElementsByClassName("js-slide-image");
    var dots = document.getElementsByClassName("js-slide-hover");
    var totalSlides = x.length;

    if (n >= totalSlides) {
        slideIndex = 0;
    }
    if (n < 0) {
        slideIndex = totalSlides - 1;
    }

    var slideList = document.querySelector(".slide__list");
    slideList.style.transform = `translateX(${-slideIndex * 100}%)`;

    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" slide__white", "");
    }
    dots[slideIndex].className += " slide__white";
}

// End slide show

window.addEventListener("load", () => {
    const switchBtn = document.querySelector("#switch-theme-btn");
    if (switchBtn) {
        switchBtn.onclick = function () {
            const isDark = localStorage.dark === "true";
            document.querySelector("html").classList.toggle("dark", !isDark);
            localStorage.setItem("dark", !isDark);
            switchBtn.querySelector("span").textContent = isDark ? "Chế độ ban đêm" : "Chế độ ban ngày";
        };
        const isDark = localStorage.dark === "true";
        switchBtn.querySelector("span").textContent = isDark ? "Chế đố ban ngày" : "Chế độ ban đêm";
    }
});

//  Dịch chuyển ảnh sản phẩm trong chi tiết sản phẩm

document.addEventListener('DOMContentLoaded', function () {
    const previewList = document.getElementById('previewList');
    const thumbImages = document.querySelectorAll('.prod-preview__thumb-img');
    thumbImages.forEach((thumb, index) => {
        thumb.addEventListener('click', function() {
            thumbImages.forEach(img => img.classList.remove('prod-preview__thumb-img--current'));
            thumb.classList.add('prod-preview__thumb-img--current');
            const offset = -index * 100;
            previewList.style.transform = `translateX(${offset}%)`;
        });
    });
});


document.addEventListener('DOMContentLoaded', function() {
    const productDetailElement = document.getElementById('product-detail');
    if (productDetailElement && productDetailElement.options.length > 0) {
        const selectLoad = productDetailElement.options[0];
        const quantityTemp = selectLoad.getAttribute('data-detailQty');
        const quantityElementTemp = document.querySelector('.js-quantity-select');
        if (quantityElementTemp) {
            quantityElementTemp.textContent = quantityTemp;
        }
    }
});

window.addEventListener('DOMContentLoaded', function() {
    const productDetailElement = document.getElementById('product-detail');
    if (productDetailElement) {
        productDetailElement.addEventListener('change', function (){
            const selectedOption = this.options[this.selectedIndex];
            const price = selectedOption.getAttribute('data-price');
            const quantity = selectedOption.getAttribute('data-detailQty');
            const priceElement = document.querySelector('.js-price-select');
            const quantityElement = document.querySelector('.js-quantity-select');
            if(quantityElement) {
                quantityElement.textContent = quantity;
            } else {
                console.error('Không tìm thấy phần tử có class "js-quantity-select"');
            }
            if(priceElement) {
                priceElement.textContent = formatDecimal(price) + 'đ';
            } else {
                console.error('Không tìm thấy phần tử có class "js-price-select"');
            }
        });
    } else {
        console.error('Không tìm thấy phần tử có id "product-detail"');
    }
});

function formatDecimal(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


// Load image review
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


const isDark = localStorage.dark === "true";
document.querySelector("html").classList.toggle("dark", isDark);
