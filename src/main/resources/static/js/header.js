window.onload = function(){
    console.log('logged_header.js')
    function verTooltip(event){
        var tooltip = document.getElementById('tooltipId');
        tooltip.style.visibility = 'visible';
     }

     var oUserButton = $('#buttonUserLoginId');
     oUserButton.click(verTooltip);

};