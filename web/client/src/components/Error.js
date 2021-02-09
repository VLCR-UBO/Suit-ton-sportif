import React from 'react'

export default function Error() {
    return (
        <div>
            <h1 style={h1Style} >Error 404 : la page que vous voulez n'as pas été trouvée</h1>
        </div>
    )
}

const h1Style = {
    color : "red",
    textAlign : "center",
    padding : "10px"


}