function getItems() {
    return document.getElementById("grid");
}
function sortByPopularity(Activator) {
    const list = getItems();
    const items = Array.from(list.querySelectorAll(".container"));
    items.sort((a, b) => {
        const likesA = parseInt(a.querySelector(".like-button span").textContent);
        const likesB = parseInt(b.querySelector(".like-button span").textContent);
        return likesB - likesA;
    }
    )
    items.forEach(item => list.appendChild(item));
    const modes = document.getElementById("sorting-modes");
    const children = Array.from(modes.querySelectorAll("button"));
    children.forEach(element => {
        element.className = "sorting-inactive"
    });
    Activator.className = "sorting-active";
}
function sortByDate(Activator) {
    const list = getItems();
    const items = Array.from(list.querySelectorAll(".container"));
    items.sort((a, b) => {
        const timeA = new Date(a.querySelector(".time").textContent);
        const timeB = new Date(b.querySelector(".time").textContent);
        return timeB - timeA;
    }
    )
    items.forEach(item => list.appendChild(item));
    const modes = document.getElementById("sorting-modes");
    const children = Array.from(modes.querySelectorAll("button"));
    children.forEach(element => {
        element.className = "sorting-inactive"
    });
    Activator.className = "sorting-active";
}
function sortByMakes(Activator) {
    const list = getItems();
    const items = Array.from(list.querySelectorAll(".container"));
    items.sort((a, b) => {
        const makesA = parseInt(a.querySelector(".makes").textContent);
        const makesB = parseInt(b.querySelector(".makes").textContent);
        return makesB - makesA;
    }
    )
    items.forEach(item => list.appendChild(item));
    const modes = document.getElementById("sorting-modes");
    const children = Array.from(modes.querySelectorAll("button"));
    children.forEach(element => {
        element.className = "sorting-inactive"
    });
    Activator.className = "sorting-active";
}
function trimTime(Activator, Timeframe) {
    const list = Array.from(getItems().children);
    const now = new Date();
    const msPerDay = 1000*60*60*24;
    list.forEach(item => {
        const itemDate = new Date(item.querySelector(".time").textContent);
        if (((now.getTime()-itemDate.getTime())/msPerDay) > Timeframe && Timeframe != 0) {
            item.className = "hidden"
        }
        else item.className = "container"
    })
    const modes = document.getElementById("sorting-times");
    const children = Array.from(modes.querySelectorAll("button"));
    children.forEach(element => {
        element.className = "sorting-inactive"
    });
    Activator.className = "sorting-active";
}