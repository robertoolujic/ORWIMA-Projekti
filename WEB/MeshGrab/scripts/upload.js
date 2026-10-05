import FirebaseService from "./firebase.js";
export default class UploadPage {
    constructor() {
        this.firebaseService = new FirebaseService();
        this.imageIndex = 0;
        this.itemGallery = [];
        this.updateIcons();
        document.getElementById("title-form").addEventListener("submit", function (event) {
            event.preventDefault();
        })
        document.getElementById("description-form").addEventListener("submit", function (event) {
            event.preventDefault();
        })
    }
    addImage() {
        this.itemGallery.push("https://matriarch.tax/wp-content/uploads/2023/09/placeholder-4-3.png");
        this.updateIcons();
    }
    removeImage() {
        this.itemGallery.pop("https://matriarch.tax/wp-content/uploads/2023/09/placeholder-4-3.png");
        this.updateIcons();
    }
    loadNextImage() {
        if (this.imageIndex < this.itemGallery.length - 1) this.imageIndex++;
        document.getElementById("thumbnail").setAttribute("src", this.itemGallery[this.imageIndex]);
        this.updateIcons();

    }
    loadPreviousImage() {
        if (this.imageIndex > 0) this.imageIndex--;
        document.getElementById("thumbnail").setAttribute("src", this.itemGallery[this.imageIndex]);
        this.updateIcons();
    }
    addFile() {
        let file = document.createElement("button");
        let image = document.createElement("img");
        image.setAttribute("src", "https://matriarch.tax/wp-content/uploads/2023/09/placeholder-4-3.png");
        file.appendChild(image);
        file.setAttribute("onclick", "uploadPage.removeFile(this)");
        document.getElementById("files").appendChild(file);
    }
    removeFile(Activator) {
        document.getElementById("files").removeChild(Activator);
    }
    updateIcons() {
        document.getElementById("previousImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26146&format=png&color=009760");
        document.getElementById("nextImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26147&format=png&color=009760");
        if (this.imageIndex == 0)
            document.getElementById("previousImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26146&format=png&color=AAAAAA");
        if (this.imageIndex >= this.itemGallery.length - 1)
            document.getElementById("nextImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26147&format=png&color=AAAAAA");
        if (this.itemGallery.length > 0) {
            document.getElementById("removeImageIcon").setAttribute("class", "");
            document.getElementById("thumbnail").setAttribute("src", this.itemGallery[this.itemGallery.length - 1]);
        }
        else {
            document.getElementById("removeImageIcon").setAttribute("class", "hidden");
            document.getElementById("thumbnail").setAttribute("src", "https://www.photobookseh.ca/storage/designer/969/pages/12212.jpg?cache=1302618539");
        }
    }
    uploadModel() {
        let title = document.getElementById("title-field").value;
        let description = document.getElementById("description-field").value;
        if (title != null && description != null && this.itemGallery.length > 0) {
            this.firebaseService.uploadModel(
                title,
                description,
                this.itemGallery,
                "aqecIWECEbTSH1odriz9",
                []
            )
        }
    }
}
let uploadPage = new UploadPage();
window.uploadPage = uploadPage;