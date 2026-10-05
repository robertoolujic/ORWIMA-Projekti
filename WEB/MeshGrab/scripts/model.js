import FirebaseService from "./firebase.js";
import likeButton from "./likeButton.js";
const params = new URLSearchParams(window.location.search);
const modelId = params.get("id");

export default class ModelPage {
  constructor(modelId) {
    this.imageIndex = 0;
    this.firebaseService = new FirebaseService();
    this.firebaseService.fetchModels((models) => {
      this.model = models.find(model => model.id == modelId)
      this.Build();
    })
    this.firebaseService.fetchUsers((users) => {
      this.user = users.find(user => user.id == this.model.creatorId);
      this.Build();
    })
    this.firebaseService.fetchCollections((collections) => {
      this.collections = collections;
      this.Build();
    })

  }
  Build() {
    if (this.model && this.user && this.collections) {
      if (this.like == null) {
        this.like = new likeButton(
          this.model.id,
          this.user.id,
          this.collections.find(collection =>
            collection.creatorId == "aqecIWECEbTSH1odriz9"
            && collection.title == "Liked Models").id,
          this.firebaseService);
        document.getElementById("like-button").replaceWith(this.like.likeButton);
      }
      document.title = "Model: " + this.model.title + " | MeshGrab";
      document.getElementById("title").textContent = this.model.title;
      document.getElementById("description").textContent = this.model.description;
      document.getElementById("thumbnail").setAttribute("src", this.model.gallery[this.imageIndex]);
      document.getElementById("creator").setAttribute("href", "user.html?id=" + this.user.id);
      document.getElementById("avatar").setAttribute("src", this.user.avatar);
      document.getElementById("name").textContent = this.user.name;
      this.updateLoadIcons();
    }
  }
  loadNextImage() {
    if (this.imageIndex < this.model.gallery.length - 1) this.imageIndex++;
    document.getElementById("thumbnail").setAttribute("src", this.model.gallery[this.imageIndex]);
    this.updateLoadIcons();

  }
  loadPreviousImage() {
    if (this.imageIndex > 0) this.imageIndex--;
    document.getElementById("thumbnail").setAttribute("src", this.model.gallery[this.imageIndex]);
    this.updateLoadIcons();
  }
  updateLoadIcons(){
    document.getElementById("previousImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26146&format=png&color=009760");
    document.getElementById("nextImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26147&format=png&color=009760");
    if(this.imageIndex==0)
      document.getElementById("previousImageIcon").setAttribute("src","https://img.icons8.com/?size=100&id=26146&format=png&color=AAAAAA");
    if(this.imageIndex == this.model.gallery.length-1)
      document.getElementById("nextImageIcon").setAttribute("src", "https://img.icons8.com/?size=100&id=26147&format=png&color=AAAAAA");
  }
}

let modelPage = new ModelPage(modelId);
window.modelPage = modelPage;