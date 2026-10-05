import FirebaseService from "./firebase.js";
import likeButton from "./likeButton.js";
export default class GridBuilder {
    constructor(filter) {
        this.firebaseService = new FirebaseService();
        this.filter = filter;
        this.init();
    }
    init() {
        this.firebaseService.fetchModels((models) => {
            this.models = this.filter(models);
            this.Build();
        })
        this.firebaseService.fetchUsers((users) => {
            this.users = users;
            this.Build();
        })
        this.firebaseService.fetchCollections((collections) => {
            this.collections = collections;
            this.Build();
        })
    }
    Build() {
        if (this.models && this.users && this.collections) {
            document.getElementById("grid").innerHTML="";
            this.models.forEach(model => {
                this.modelContainer(
                    model,
                    this.users.find(user => user.id == model.creatorId),
                    this.collections.find(collection =>
                        collection.creatorId == "aqecIWECEbTSH1odriz9"
                        && collection.title=="Liked Models"));
            })
        }
    }
    modelContainer(model, user, collection) {
        let container = document.createElement("div");
        let time = document.createElement("span");
        let makes = document.createElement("span");
        let frame = document.createElement("a");
        let thumbnail = document.createElement("img");
        let info = document.createElement("div");
        let title = document.createElement("a");
        let like = new likeButton(model.id, user.id, collection.id, this.firebaseService);
        let creator = document.createElement("a");
        let profile = document.createElement("img");
        let name = document.createElement("span");
        container.id = model.id;
        time.textContent = model.dateUploaded.toDate();
        makes.textContent = model.makes;
        thumbnail.setAttribute("src", model.gallery[0]);
        title.textContent = model.title;
        creator.id = model.creatorId;
        profile.setAttribute("src", user.avatar);
        name.textContent = user.name;

        title.href = "model.html?id=" + model.id;
        frame.href = "model.html?id=" + model.id;
        creator.href = "user.html?id=" + user.id;
        container.className = "container";
        time.className = "time";
        makes.className = "makes";
        thumbnail.className = "thumbnail";
        info.className = "info";
        title.className = "title";
        creator.className = "creator";

        container.appendChild(frame);
        container.appendChild(time);
        container.appendChild(makes);
        container.appendChild(info);
        container.appendChild(creator);
        frame.appendChild(thumbnail);
        info.appendChild(title);
        info.appendChild(like.likeButton);
        creator.appendChild(profile);
        creator.appendChild(name);
        document.getElementById("grid").appendChild(container);
    }
}
