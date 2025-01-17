const backendBaseURL = "http://localhost:8086"

export const getData = async (endpoint) => {
    console.log("Get request calling " + endpoint);

    try {
        const response = await fetch(`${backendBaseURL}/${endpoint}`)
        const data = response.json();
        return data;
    } catch (error) {
        console.error("Error fetching data: response may be null", error);
        throw error;
    }
}

export const postData = async (endpoint, body) => {
    console.log(`Post request calling ${endpoint}`, body);

    try {
        const response = await fetch(`${backendBaseURL}/${endpoint}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        })
        const data = response.json();
        return data;
    } catch (error) {
        console.error("Error posting data", error);
        throw error;
    }
};

export const putData = async (endpoint, body) => {
    console.log(`Put request calling ${endpoint}`, body);

    try {
        const response = await fetch(`${backendBaseURL}/${endpoint}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(body),
        });
        return response.json();
    } catch (error) {
        console.error('Error:', error);
        return { hasError: true, error: error.message };
    }
};
