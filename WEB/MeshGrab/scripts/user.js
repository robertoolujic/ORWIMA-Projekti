import FirebaseService from "./firebase.js";
import GridBuilder from "./gridBuilder.js";

const params = new URLSearchParams(window.location.search);
const userId = params.get("id");

if (!userId) {
  document.body.innerHTML = "No user ID provided";
  throw new Error("Missing ID");
}

export default class UserPage {
  constructor(userId) {
    this.firebaseService = new FirebaseService();
    this.firebaseService.fetchUsers((users) => {
      this.user = users.find(user => user.id == userId);
      this.Build();
    })
    this.firebaseService.fetchModels((models) => {
      this.models = models.filter(model => model.creatorId == userId)
      this.Build();
    })
    this.firebaseService.fetchCollections((collections) => {
      this.collections = collections;
      this.Build();
    })
  }
  Build() {
    if (this.user && this.models && this.collections) {
      document.getElementById("name").textContent = this.user.name;
      document.getElementById("bio").textContent = this.user.bio;
      document.getElementById("avatar").setAttribute("src", this.user.avatar);
      document.getElementById("following").textContent = this.user.following;
      document.getElementById("followers").textContent = this.user.followers;
      document.getElementById("likes").textContent = this.user.likes;
      document.title = "User: " + this.user.name + " | MeshGrab";
      let userCollections = this.collections.filter(it => it.creatorId == userId);
      document.getElementById("collections").innerHTML="";
      userCollections.forEach(it => {this.collectionContainer(it)});
      
    }
  }
   collectionContainer(collection){
    let container = document.createElement("a");
    let thumbnail = document.createElement("img");
    let info = document.createElement("div");
    let title = document.createElement("span");
    let date = document.createElement("span");

    container.id = collection.id;
    thumbnail.setAttribute("src", collection.gallery[0]);
    info.id = "collection-info";
    title.textContent = collection.title;
    date.textContent = collection.dateUploaded.toDate().toLocaleString();
    container.href = "collection.html?id=" + collection.id;
    
    container.appendChild(thumbnail);
    container.appendChild(info);
    info.appendChild(title);
    info.appendChild(date);
    document.getElementById("collections").appendChild(container);
}
}

new UserPage(userId);
new GridBuilder((models) => { return models.filter(m => m.creatorId == userId)});
