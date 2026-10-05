import FirebaseService from "./firebase.js";
import GridBuilder from "./gridBuilder.js";

const params = new URLSearchParams(window.location.search);
const collectionId = params.get("id");

if (!collectionId) {
    document.body.innerHTML = "No collection ID provided";
    throw new Error("Missing ID");
}

export default class CollectionPage {
    constructor(collectionId) {
        this.imageIndex = 0;
        this.firebaseService = new FirebaseService();
        this.firebaseService.fetchCollections((collections) => {
            this.collection = collections.find(collection => collection.id == collectionId);
            this.Build();
        })
        this.firebaseService.fetchUsers((users) => {
            this.user = users.find(user => user.id == this.collection.creatorId);
            this.Build();
        })
    }

    Build() {
        if (this.user && this.collection) {
            document.title = "Collection: " + this.collection.title + " | MeshGrab";
            document.getElementById("title").textContent = this.collection.title;
            document.getElementById("description").textContent = this.collection.description;
            document.getElementById("photos").setAttribute("src", this.collection.gallery[this.imageIndex]);
            document.getElementById("creator").setAttribute("href", "user.html?id=" + this.user.id);
            document.getElementById("avatar").setAttribute("src", this.user.avatar);
            document.getElementById("name").textContent = this.user.name;
            this.updateLoadIcons();
        }
    }
    loadNextImage() {
        if (this.imageIndex < this.model.gallery.length - 1) this.imageIndex++;
        document.getElementById("photos").setAttribute("src", this.collection.gallery[this.imageIndex]);
        this.updateLoadIcons();

    }
    loadPreviousImage() {
        if (this.imageIndex > 0) this.imageIndex--;
        document.getElementById("photos").setAttribute("src", this.collection.gallery[this.imageIndex]);
        this.updateLoadIcons();
    }
    updateLoadIcons() {
        document.getElementById("previousImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26146&format=png&color=009760");
        document.getElementById("nextImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26147&format=png&color=009760");
        if (this.imageIndex == 0)
            document.getElementById("previousImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26146&format=png&color=AAAAAA");
        if (this.imageIndex >= this.collection.gallery.length - 1)
            document.getElementById("nextImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26147&format=png&color=AAAAAA");
    }
}

let collectionPage = new CollectionPage(collectionId);
new GridBuilder((models) => { return models.filter(model => collectionPage.collection.models.includes(model.id)) });