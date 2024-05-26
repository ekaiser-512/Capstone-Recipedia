
import './Dark.css'

const Toggle = ({className, onChange}) => {
    return (
        <label className={`switch ${className}`}>
            <input type="checkbox" onChange={onChange}/>
            <span className="slider"></span>
        </label>

    )
}

export default Toggle