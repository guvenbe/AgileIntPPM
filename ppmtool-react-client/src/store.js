import {createStore, applyMiddleWare, compose} from "redux";
import thunk from "react-thunk";
import rootReducer from "./reducers";

const initialState = {};
const middleware =  [thunk];

let store;

if (window.navigator.userAgent.includes("Chrome")){
    store=createStore(rootReducer,
                        initialState,
                        compose(applyMiddleWare(...middleware),
                            window.__REDUX_DEVTOOLS_EXTENSION__
                            && window.__REDUX_DEVTOOLS_EXTENSION__()
        )
    );
} else{
    store=createStore(
        rootReducer,
        initialState,
        compose(
            applyMiddleWare(...middleware))
        );

}

export default store;
