window.onload = function(){

    console.log("logged pago");

    function mostrarTotalConDto(event){
        const dto = 0.3;
        let totalPago = parseFloat($("#_totalId").get(0).children[0].innerText);
        let h5ConDto = $("#_totalConDtoId").get(0);
        let divSaldoInsuf = $("#_idSaldoInsuf").get(0);
        let butPagar = $("#_buttonSubmitPagoId").get(0);
        let saldoTarjeta= parseFloat($("#_idSaldoTarjeta").get(0).innerText);
        let importeAUsarCred = $("#_importeCreditosAGastarId").get(0);

        if (h5ConDto.style.display === "none") {
            h5ConDto.style.display = "block";
            importeAUsarCred.innerText = 400;
            totalPago *= (1 - dto);
        } else {
            h5ConDto.style.display = "none";
            importeAUsarCred.innerText = 0;
        }

        butPagar.disabled = (saldoTarjeta < totalPago) ? true : false;
        divSaldoInsuf.style.display = (saldoTarjeta < totalPago) ? "initial" : "none";
    }

     let oButtonSelectPagoCred = $('#_pagarConCredId');
        oButtonSelectPagoCred.click(mostrarTotalConDto);



};