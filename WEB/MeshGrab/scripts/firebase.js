
import { getFirestore, serverTimestamp, collection, onSnapshot, getDoc, addDoc, updateDoc, arrayUnion, arrayRemove, increment, doc } from "https://www.gstatic.com/firebasejs/12.8.0/firebase-firestore.js";
import { initializeApp } from "https://www.gstatic.com/firebasejs/12.8.0/firebase-app.js";
const firebaseConfig = {
    apiKey: "AIzaSyA76wkUvHCrIObBJsWfXL1EKns5grSrU3s",
    authDomain: "meshgrab-5f51a.firebaseapp.com",
    projectId: "meshgrab-5f51a",
    storageBucket: "meshgrab-5f51a.firebasestorage.app",
    messagingSenderId: "1056737410866",
    appId: "1:1056737410866:web:73dbd64a4a978bed32a12b"
};


export default class FirebaseService {
    constructor() {
        initializeApp(firebaseConfig);
        this.db = getFirestore();
    }
    fetchModels(callback) {
        const ref = onSnapshot(collection(this.db, "models"), (snapshot) => {
            const models = snapshot.docs.map(doc => ({
                id: doc.id,
                ...doc.data()
            }));
            callback(models);
        });
    }
    fetchUsers(callback) {
        const ref = onSnapshot(collection(this.db, "users"), (snapshot) => {
            const users = snapshot.docs.map(doc => ({
                id: doc.id,
                ...doc.data()
            }))
            callback(users);
        });
    }
    fetchCollections(callback) {
        const ref = onSnapshot(collection(this.db, "collections"), (snapshot) => {
            const collections = snapshot.docs.map(doc => ({
                id: doc.id,
                ...doc.data()
            }))
            callback(collections);
        });
    }
    async modelInCollection(modelId, collectionId) {
        const modelDoc = await getDoc(doc(this.db, "collections", collectionId));
        const modelList = modelDoc.data().models;
        return modelList.includes(modelId);
    }
    async addToCollection(modelId, collectionId) {
        if (await this.modelInCollection(modelId, collectionId)) return false;
        await updateDoc(doc(this.db, "collections", collectionId), {
            models: arrayUnion(modelId)
        });
        console.log("Added " + modelId + " to "+ collectionId);

        return true;
    }
    async removeFromCollection(modelId, collectionId) {
        if (await !this.modelInCollection(modelId, collectionId)) return false;
        await updateDoc(doc(this.db, "collections", collectionId), {
            models: arrayRemove(modelId)
        });
        console.log("Removed " + modelId + " from "+ collectionId);
        return true;
    }
    async toggleInCollection(modelId, collectionId) {
        if (await this.modelInCollection(modelId, collectionId)) {
            await updateDoc(doc(this.db, "collections", collectionId), {
                models: arrayRemove(modelId)
            });
            return false;
        }
        await updateDoc(this.getCollection(collectionId), {
            models: arrayUnion(modelId)
        });
        return true;
    }
    async updateLikes(modelId, userId, value){
        await updateDoc(
                doc(this.db, "models", modelId), {
                likes: increment(value)
            });
        await updateDoc(
                doc(this.db, "users", userId), {
                likes: increment(value)
            });
    }
    async uploadModel(Title, Description, Gallery, CreatorId, Files){
        const docRef = await addDoc(collection(this.db, "models"),{
            title: Title,
            description: Description,
            gallery: Gallery,
            creatorId: CreatorId,
            dateUploaded: serverTimestamp(),
            files: Files,
            makes: 0,
            likes: 0,
            downloads: 0
        });
        window.location.href = "model.html?id="+docRef.id;
    }
}