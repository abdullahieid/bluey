// import fetch from "node-fetch";
//
// // basically this is how i uploaded the blob bc i kept getting 413 with java and gave up...
//
// export interface Credentials {
//     identifier: String;
//     password : String;
//     provider : String;
// }
//
// export interface Session {
//     did: String
//     handle: String
//     email: String
//     accessJwt: String
//     refreshJwt: String
// }
//
// // add real creds here
// const credentials: Credentials = {
//     identifier: "$USERNAME",
//     password: "$PASSWORD",
//     provider: "bsky.social"
// };
//
// const getLogin = async(login: Credentials): Promise<Session>  => {
//     const res = await fetch(`https://${credentials.provider}/xrpc/com.atproto.server.createSession`, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(login)
//     });
//
//     console.log(res.status);
//     console.log(res.statusText);
//
//     return await res.json();
// }
//
// (async function () {
//     const session: Session = await getLogin(credentials);
//
//     const response = await fetch("https://i.kym-cdn.com/entries/icons/original/000/039/638/michaeljordanfuckthemkids.jpg");
//     const image_bytes: Uint8Array = await response.arrayBuffer().then((buf) => new Uint8Array(buf));
//
//     const res = await fetch(`https://${credentials.provider}/xrpc/com.atproto.repo.uploadBlob`, {
//         method: 'POST',
//         headers: {
//             'Authorization': `Bearer ${session.accessJwt}`,
//             'Content-Type': 'image/jpeg',
//         },
//         body: image_bytes
//     });
//
//     console.log(res.status);
//     console.log(res.statusText);
//
//     const data = await res.json();
//     console.log(data);
// })()