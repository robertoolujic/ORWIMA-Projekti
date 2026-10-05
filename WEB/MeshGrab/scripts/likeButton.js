export default class likeButton {
    constructor(modelId, userId, collectionId, firebaseService) {
        this.modelId = modelId;
        this.userId = userId;
        this.collectionId = collectionId;
        this.firebaseService = firebaseService;
        this.likeButton = document.createElement("button");
        this.likeButton.classList.add("like-button");
        this.likeIco = document.createElement("img");
        this.likeText = document.createElement("span");
        this.likeButton.appendChild(this.likeText);
        this.likeButton.appendChild(this.likeIco);
        this.fullIcon = "https://img.icons8.com/?size=100&id=7697&format=png&color=009760";
        this.emptyIcon = "https://img.icons8.com/?size=100&id=87&format=png&color=000000";
        this.init();
    }
    async init() {
        this.firebaseService.fetchModels((models) => {
            this.likeText.textContent = models.find(model => model.id == this.modelId).likes;
        })

        if (await this.firebaseService.modelInCollection(this.modelId, this.collectionId)) {
            this.src = this.fullIcon;
        }
        else {
            this.src = this.emptyIcon;
        }
        this.likeIco.setAttribute("src", this.src);
        this.likeButton.addEventListener("click", () => this.toggleState())
    }
    async toggleState() {
        if (await this.firebaseService.modelInCollection(this.modelId, this.collectionId)) {
            this.src = this.emptyIcon;
            await this.firebaseService.removeFromCollection(this.modelId, this.collectionId);
            await this.firebaseService.updateLikes(this.modelId, this.userId, -1);
        }
        else {
            this.src = this.fullIcon;
            await this.firebaseService.addToCollection(this.modelId, this.collectionId);
            await this.firebaseService.updateLikes(this.modelId, this.userId, 1);
            
        }
        this.likeIco.setAttribute("src", this.src);
    }
}