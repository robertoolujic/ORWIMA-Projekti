import FirebaseService from "./firebase.js";
import GridBuilder from "./gridBuilder.js";




export default class HomePage {
    constructor() {
        this.fuseOptions = {keys: ["title","description"]};
        this.firebaseService = new FirebaseService();
        this.GridBuilder = new GridBuilder((it) => { return it });
        window.addEventListener("DOMContentLoaded", () => {
            const params = new URLSearchParams(window.location.search);
            this.query = params.get("q");
            params.set("q", this.query);
            document.getElementById("search-field").value = this.query;
            
        })
        this.firebaseService.fetchModels((models) => {
            this.models = models;
            this.fuse = new Fuse(this.models, this.fuseOptions);
            this.modelSearch(this.query);
        })
    }

    modelSearch(searchPattern) {
        document.getElementById("grid").innerHTML="";
        const result = searchPattern ? this.fuse.search(searchPattern).map(r => r.item) : this.models;
        this.GridBuilder.models = result;
    }
}
new HomePage();