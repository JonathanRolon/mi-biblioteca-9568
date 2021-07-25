window.onload = function(){
    console.log("logged");

    /*** hilo.html ***/
    function eraseText(event){
        document.getElementById("_idRespuesta").value = "";
    }

    let oButtonLimpiarResp = $('#_idButtonLimpiarTextArea');
    oButtonLimpiarResp.click(eraseText);

};