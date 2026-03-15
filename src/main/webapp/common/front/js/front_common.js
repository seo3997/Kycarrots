// Front Common Scripts
// Handles global loading indicators and front-end specific UI logic

(function() {
    // Inject loading overlay HTML
    const overlayHtml = `
        <div id="loadingOverlay" class="loading-overlay" style="display:none;">
            <div class="spinner"></div>
        </div>
    `;

    function injectOverlay() {
        if (document.getElementById('loadingOverlay')) return;
        const div = document.createElement('div');
        div.innerHTML = overlayHtml;
        document.body.appendChild(div.firstElementChild);
    }

    // Global loading functions
    window.showLoading = function() {
        const overlay = document.getElementById('loadingOverlay');
        if (overlay) overlay.style.display = 'flex';
    };

    window.hideLoading = function() {
        const overlay = document.getElementById('loadingOverlay');
        if (overlay) overlay.style.display = 'none';
    };

    // Auto-bind events
    const init = function() {
        injectOverlay();

        // 1. Hide on page full load
        window.addEventListener('load', hideLoading);

        // 2. Show on navigating away
        document.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', function(e) {
                const href = this.getAttribute('href');
                const target = this.getAttribute('target');
                if (href && !href.startsWith('#') && !href.startsWith('javascript') && target !== '_blank') {
                    showLoading();
                }
            });
        });

        // 3. Show on form submit
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function() {
                // Only show if not prevented by other scripts
                setTimeout(() => {
                    if (!this.defaultPrevented) showLoading();
                }, 10);
            });
        });

        // 4. JQuery AJAX integration (if present)
        if (window.jQuery) {
            $(document).ajaxStart(showLoading).ajaxStop(hideLoading);
        }
    };

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();
