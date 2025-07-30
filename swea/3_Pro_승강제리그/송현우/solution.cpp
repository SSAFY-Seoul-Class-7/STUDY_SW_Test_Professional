#include <vector>
#include <set>
#include <algorithm>
#include <iterator>

using namespace std;

struct P {
    int id;
    int abil;

    bool operator<(const P& other) const {
        if (abil != other.abil) {
            return abil > other.abil;
        }
        return id < other.id;
    }
};

struct League {
    set<P> l;
    set<P> r;

    League() = default;
    League(int idx, const vector<int>& abilv) {
        vector<P> pv;
        for (int i = 0; i < abilv.size(); i++) {
            pv.push_back({ idx + (int)i, abilv[i] });
        }

        sort(pv.begin(), pv.end());

        for (int i = 0; i < pv.size() / 2; i++) {
            l.insert(pv[i]);
        }
        for (int i = pv.size() / 2; i < pv.size(); i++) {
            r.insert(pv[i]);
        }
    }

    P eraseP(set<P>& s, bool is_first) {
        auto it = is_first ? s.begin() : prev(s.end());
        P p = *it;
        s.erase(it);
        return p;
    }

    P getWorst() {
        return eraseP(r, false);
    }

    P getBest() {
        return eraseP(l, true);
    }

    P getMiddle() {
        return eraseP(r, true);
    }

    void insertP(const P& p) {
        if (p.abil <= r.begin()->abil) {
            r.insert(p);
        }
        else {
            l.insert(p);
        }

        while (l.size() > r.size()) {
            r.insert(eraseP(l, false));
        }
        while (l.size() + 1 < r.size()) {
            l.insert(eraseP(r, true));
        }
    }
};

vector<League> leagues;

void init(int N, int L, int mAbility[]) {
    leagues.assign(L, League());
    int size = N / L;
    for (int i = 0; i < L; i++) {
        vector<int> abilv;
        for (int j = 0; j < size; j++) {
            abilv.push_back(mAbility[i * size + j]);
        }
        leagues[i] = League(i * size, abilv);
    }
}

int move() {
    int res = 0;
    vector<P> worst;
    vector<P> best;
    for (size_t i = 1; i < leagues.size(); i++) {
        P w = leagues[i - 1].getWorst();
        P b = leagues[i].getBest();
        worst.push_back(w);
        best.push_back(b);
        res += w.id + b.id;
    }
    for (int i = 0; i < worst.size(); i++) {
        leagues[i + 1].insertP(worst[i]);
    }
    for (int i = 0; i < best.size(); i++) {
        leagues[i].insertP(best[i]);
    }
    return res;
}

int trade() {
    int res = 0;
    vector<P> best;
    vector<P> middle;
    for (int i = 1; i < leagues.size(); i++) {
        P b = leagues[i].getBest();
        P m = leagues[i - 1].getMiddle();
        best.push_back(b);
        middle.push_back(m);
        res += b.id + m.id;
    }
    for (int i = 0; i < best.size(); i++) {
        leagues[i].insertP(best[i]);
    }
    for (int i = 0; i < middle.size(); i++) {
        leagues[i + 1].insertP(middle[i]);
    }
    return res;
}
