function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
}

 class DataClass {
    constructor() {
        //Glitter
        this.glitter = window.parent.glitter
        //此頁面的tag
        this.pageTag = getUrlParameter('tag')
        //夾帶過來的資料
        this.jsonData = window.parent.glitter.getJsonBundle(this.pageTag)
    }
}

var BundleData=new DataClass()



