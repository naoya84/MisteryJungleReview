export interface Http {
    get<T>(url: string): Promise<T>
    post<T>(url: string, body: string): Promise<T>
}

export class NetworkHttp implements Http {
    async get<T>(url: string): Promise<T> {
        const response = await fetch(url)
        return await response.json() as Promise<T>
    }

    async post<T>(url: string, body: string): Promise<T> {
        const headers = {"Content-Type": "application/json"}
        const method = "POST"

        const response = await fetch(url, {method, headers, body})
        return await response.json() as Promise<T>
    }
}